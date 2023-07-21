(ns shape.handlers.admin.setup
  (:require
    [clojure.string :as string]
    [ring.util.anti-forgery :refer [anti-forgery-field]]
    [crypto.password.bcrypt :as password]
    [shape.data :as data]
    [shape.handlers.utils :refer [->redirect ->set-cookie ->page]]
    [shape.utils :refer [->merge]]))

(defn- view-handler-page [request]
  [:div.setup-content
   [:div.logo]
   [:h2 "Welcome, stranger."]
   [:p "Let's get to know each other."]
   [:form {:method "post"}
      (when-let [error (:error request)]
        [:div.error error])
      (anti-forgery-field)
      [:label "E-mail"
       [:input {:type "email"
                :placeholder "E-mail"
                :value (-> request :remembered :email)
                :name "email"}]]
      [:label "Password"
       [:input {:type "password"
                :placeholder "Password"
                :name "password"}]]
      [:button {:type "submit"
                :class "primary"}
       "Set up"]]])

(defn view-handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->page
           (view-handler-page request)
           {:css ["setup"]
            :body-class "setup"})})

(defn- create-user-and-set-token! [email password token]
  (let [user-id (data/create-user! {:email email
                                    :password (password/encrypt password)
                                    :role "developer"})]
    (data/set-user-token! user-id token)))

(defn action-handler
  [request]
  (let [email (get-in request [:form-params "email"])
        password (get-in request [:form-params "password"])
        token (str (random-uuid))]
    (cond
      (string/blank? email)
      (view-handler (-> request
                        (assoc :error "E-mail is required.")
                        (assoc-in [:remembered :email] email)))

      (string/blank? password)
      (view-handler (-> request
                        (assoc :error "Password is required.")
                        (assoc-in [:remembered :email] email)))

      :else
      (do
        (create-user-and-set-token! email password token)
        (->merge (->redirect "/admin")
                 (->set-cookie "_shape_token" token))))))
