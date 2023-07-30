(ns shape.routes.admin
  (:require
   [shape.handlers.admin.dashboard :as handlers.admin.dashboard]
   [shape.handlers.admin.login :as handlers.admin.login]
   [shape.handlers.admin.logout :as handlers.admin.logout]
   [shape.handlers.admin.setup :as handlers.admin.setup]
   [shape.handlers.admin.forgot-password :as handlers.admin.forgot-password]
   [shape.handlers.admin.reset-password :as handlers.admin.reset-password]
   [shape.handlers.admin.content :as handlers.admin.content]
   [shape.handlers.admin.add-content :as handlers.admin.add-content]
   [shape.handlers.admin.content-item :as handlers.admin.content-item]
   [shape.middlewares :as middlewares]))

(def ^:private dashboard
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-authenticated?]
         :handler handlers.admin.dashboard/handler}})

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

(def ^:private logout
  {:get {:responses {200 {:body string?}}
         :handler handlers.admin.logout/handler}})

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

(def ^:private content
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?
                      middlewares/is-authenticated?
                      middlewares/shape-exists?]
         :handler handlers.admin.content/handler}})

(def ^:private add-content
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?
                      middlewares/is-authenticated?
                      middlewares/shape-exists?]
         :handler handlers.admin.add-content/handler}})

(def ^:private content-item
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?
                      middlewares/is-authenticated?
                      middlewares/shape-exists?]
         :handler handlers.admin.content-item/handler}})

(def routes
  [["" dashboard]
   ["/setup" setup]
   ["/login" login]
   ["/logout" logout]
   ["/forgot-password" forgot-password]
   ["/reset-password/:token" reset-password]
   ["/content/:identifier" content]
   ["/content/:identifier/add" add-content]
   ["/content/:identifier/item/:id" content-item]])
