(ns shape.handlers.admin.setup
  (:require
    [clojure.string :as string]
    [ring.util.anti-forgery :refer [anti-forgery-field]]
    [hiccup.page :refer [html5]]
    [crypto.password.bcrypt :as password]
    [shape.data :as data]
    [shape.handlers.utils :refer [->redirect ->set-cookie]]
    [shape.utils :refer [->merge]]))

(defn view-handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5
           [:div
            (when-let [error (:shape-error request)]
              [:div.error error])
            [:form {:method "post"}
             (anti-forgery-field)
             [:input {:type "email"
                      :placeholder "E-mail"
                      :name "email"}]
             [:input {:type "password"
                      :placeholder "Password"
                      :name "password"}]
             [:button {:type "submit"} "Create account and sign in"]]])})

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
      (view-handler (assoc request :shape-error "E-mail is required."))

      (string/blank? password)
      (view-handler (assoc request :shape-error "Password is required."))

      :else
      (do
        (create-user-and-set-token! email password token)
        (->merge (->redirect "/admin")
                 (->set-cookie "_shape_token" token))))))
