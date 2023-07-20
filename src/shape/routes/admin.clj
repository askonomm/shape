(ns shape.routes.admin
  (:require
    [shape.handlers.admin.login :as handlers.admin.login]
    [shape.handlers.admin.setup :as handlers.admin.setup]
    [shape.middlewares :as middlewares]))

(def ^:private dashboard
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-authenticated?]
         :handler (fn [_] 
                    {:status 200
                     :body "Hello from admin"})}})

(def ^:private setup
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/isnt-setup?]
         :handler handlers.admin.setup/view-handler}
   :post {:responses {200 {:body string?}}
          :middleware [middlewares/isnt-setup?]
          :handler handlers.admin.setup/action-handler}})

(def ^:private login
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?]
         :handler handlers.admin.login/view-handler}
   :post {:responses {200 {:body string?}}
          :middlewares [middlewares/is-setup?]
          :handler handlers.admin.login/action-handler}})

(def routes
  [["" dashboard]
   ["/setup" setup]
   ["/login" login]])

