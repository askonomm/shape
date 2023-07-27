(ns shape.handlers.admin.login
  (:require
     [ring.util.anti-forgery :refer [anti-forgery-field]]
     [shape.handlers.utils :refer [->page ->redirect ->set-cookie]]
     [shape.utils :refer [->merge]]
     [shape.data :as data]
     [clojure.string :as string]))

(defn- view-handler-page [request]
  [:div.wall-content
    [:div.logo]
    [:h2 "Log in"]
    [:p "Let's get to work."]
    [:form {:method "post"}
     (when-let [x (get-in request [:query-params "x"])]
       (cond
         (= x "password-changed")
         [:div.success "Password changed. You can now log in."]))
     (when-let [error (:error request)]
       [:div.error error])
     (anti-forgery-field)
     [:label "E-mail"
      [:input {:type "email"
               :placeholder "you@somewhere.com"
               :value (-> request :remembered :email)
               :name "email"}]]
     [:label "Password"
      [:input {:type "password"
               :name "password"}]]
     [:button {:type "submit"
               :class "primary"} "Log in"]]
    [:a {:href "/admin/forgot-password"} "Forgot password?"]])

(defn view-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->page
          (view-handler-page request)
          {:css ["wall"]
           :body-class "wall"})})

(defn action-handler [request]
  (let [email (get-in request [:form-params "email"])
        password (get-in request [:form-params "password"])
        token (str (random-uuid))]
    (cond
      (string/blank? email)
      (view-handler (assoc request :error "E-mail is required."))

      (string/blank? password)
      (view-handler (-> request
                        (assoc :error "Password is required.")
                        (assoc-in [:remembered :email] email)))

      (not (data/user-exists-by-email? email))
      (view-handler (-> request
                        (assoc :error "No such account found.")
                        (assoc-in [:remembered :email] email)))

      (not (data/user-authenticates? email password))
      (view-handler (-> request
                        (assoc :error "Password is incorrect.")
                        (assoc-in [:remembered :email] email)))

      :else
      (let [user (data/user-by-email email)]
        (data/set-user-token! (:id user) token)
        (data/set-user-reset-token! (:id user) "")
        (->merge (->redirect "/admin")
                 (->set-cookie "_shape_token" token))))))
