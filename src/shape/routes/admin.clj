(ns shape.routes.admin
  (:require
    [shape.handlers.admin.login :as handlers.admin.login]
    [shape.middlewares :as middlewares]))

(def ^:private dashboard
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-authenticated?]
         :handler (fn [_] 
                    {:status 200
                     :body "Hello from admin"})}})

(def ^:private login
  {:get {:responses {200 {:body string?}}
         :handler handlers.admin.login/handler}})

(def routes
  [["" dashboard]
   ["/login" login]])

