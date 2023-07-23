(ns shape.routes.admin
  (:require
    [shape.handlers.admin.login :as handlers.admin.login]
    [shape.handlers.admin.setup :as handlers.admin.setup]
    [shape.handlers.admin.forgot-password :as handlers.admin.forgot-password]
    [shape.handlers.admin.reset-password :as handlers.admin.reset-password]
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

(def ^:private forgot-password
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?]
         :handler handlers.admin.forgot-password/view-handler}
   :post {:responses {200 {:body string?}}
          :middlewares [middlewares/is-setup?]
          :handler handlers.admin.forgot-password/action-handler}})

(def ^:private reset-password
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?]
         :handler handlers.admin.reset-password/view-handler}
   :post {:responses {200 {:body string?}}
          :middleware [middlewares/is-setup?]
          :handler handlers.admin.reset-password/action-handler}})

(def routes
  [["" dashboard]
   ["/setup" setup]
   ["/login" login]
   ["/forgot-password" forgot-password]
   ["/reset-password/:token" reset-password]])
