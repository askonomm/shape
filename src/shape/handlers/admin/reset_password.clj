(ns shape.handlers.admin.reset-password
  (:require
   [shape.handlers.utils :refer [->admin-page ->redirect]]
   [shape.data :as data]))

(defn- view-handler-page [request]
  (let [token (-> request :path-params :token)]
    [:div.wall-content
     [:div.logo]
     [:h2 "Reset Password"]
     [:form {:method "post"}
      (when-let [error (:error request)]
        [:div.error error])
      (if (data/user-by-reset-token token)
        (list
         [:label "New password"
          [:input {:type "password"
                   :name "password"}]]
         [:label "New password again"
          [:input {:type "password"
                   :name "password_again"}]]
         [:button {:type "submit"
                   :class "button primary"} "Change password"])
        [:p "Uh-oh, the reset token is invalid."])]]))

(defn view-handler [request]
  (->admin-page
   (view-handler-page request)
   {:css ["wall"]
    :body-class "wall"}))

(defn action-handler [request]
  (let [token (-> request :path-params :token)
        password (get-in request [:form-params "password"])
        password-again (get-in request [:form-params "password_again"])]
    (if-let [user (data/user-by-reset-token token)]
      (if (= password password-again)
        (do (data/set-user-reset-token! (:id user) "")
            (data/set-user-password! (:id user) password)
            (->redirect "/admin/login?x=password-changed"))
        (view-handler (assoc request :error "Passwords do not match.")))
      (->redirect "/admin/login"))))
