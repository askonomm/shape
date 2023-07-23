(ns shape.handlers.admin.reset-password
  (:require
   [shape.handlers.utils :refer [->page]]
   [shape.data :as data]))

(defn- view-handler-page [request]
  (let [token (-> request :path-params :token)]
    [:div.wall-content
     [:div.logo]
     [:h2 "Reset Password"]
     [:form {:method "post"}
      (if (data/user-by-reset-token token)
        (list
         [:label "New password"
          [:input {:type "password"
                   :name "password"}]]
         [:label "New password again"
          [:input {:type "password"
                   :name "password_again"}]]
         [:button {:type "submit"
                   :class "primary"} "Change password"])
        [:p "Uh-oh, the reset token is invalid."])]]))

(defn view-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->page (view-handler-page request)
                 {:css ["wall"]
                  :body-class "wall"})})

(defn action-handler [request])
