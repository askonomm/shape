(ns shape.handlers.admin.login
  (:require
    [hiccup.page :refer [html5]]
    [ring.util.anti-forgery :refer [anti-forgery-field]])) 

(defn view-handler [_]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5 {}
           [:form {:method "post"}
            (anti-forgery-field)
            [:input {:type "email"
                     :name "email"}]
            [:input {:type "password"
                     :name "password"}]
            [:button {:type "submit"} "Login"]])})
 
(defn action-handler [request]
  {:status 200
   :body ""})
