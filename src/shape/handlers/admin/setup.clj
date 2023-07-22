(ns shape.handlers.admin.setup
  (:require
    [clojure.string :as string]
    [ring.util.anti-forgery :refer [anti-forgery-field]]
    [shape.data :as data]
    [shape.handlers.utils :refer [->redirect ->set-cookie ->page]]
    [shape.utils :refer [->merge]]))

(defn- view-handler-page [request]
  [:div.wall-content
   [:div.logo]
   [:h2 "Welcome, stranger."]
   [:p "Let's get to know each other."]
   [:form {:method "post"}
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
                :placeholder ""
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
           {:css ["wall"]
            :body-class "wall"})})

(defn- create-user-and-set-token! [email password token]
  (let [user-id (data/create-user! {:email email
                                    :password password
                                    :role "developer"})]
    (data/set-user-token! user-id token)))

(defn action-handler
  [request]
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

      :else
      (do
        (create-user-and-set-token! email password token)
        (->merge (->redirect "/admin")
                 (->set-cookie "_shape_token" token))))))
