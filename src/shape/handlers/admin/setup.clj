(ns shape.handlers.admin.setup
  (:require
    [ring.util.anti-forgery :refer [anti-forgery-field]]
    [hiccup.page :refer [html5]]))

(defn view-handler
  [_]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5 {}
           [:form {:method "post"}
            (anti-forgery-field)
            [:input {:type "email"
                     :placeholder "E-mail"
                     :name "email"}]
            [:input {:type "password"
                     :placeholder "Password"
                     :name "password"}]
            [:button {:type "submit"} "Create account and sign in"]])})

(defn action-handler
  [request]
  {})