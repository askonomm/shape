(ns shape.routes.admin
  (:require
   [shape.handlers.admin.dashboard :as dashboard]
   [shape.handlers.admin.login :as login]
   [shape.handlers.admin.logout :as logout]
   [shape.handlers.admin.setup :as setup]
   [shape.handlers.admin.forgot-password :as forgot-password]
   [shape.handlers.admin.reset-password :as reset-password]
   [shape.handlers.admin.content :as content]
   [shape.handlers.admin.add-content :as add-content]
   [shape.handlers.admin.content-item :as content-item]
   [shape.handlers.admin.api.content-item.update-field :as update-field]
   [shape.middlewares :as middlewares]))

(def ^:private dashboard
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-authenticated?]
         :handler dashboard/handler}})

(def ^:private setup
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/isnt-setup?]
         :handler setup/view-handler}
   :post {:responses {200 {:body string?}}
          :middleware [middlewares/isnt-setup?]
          :handler setup/action-handler}})

(def ^:private login
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?]
         :handler login/view-handler}
   :post {:responses {200 {:body string?}}
          :middlewares [middlewares/is-setup?]
          :handler login/action-handler}})

(def ^:private logout
  {:get {:responses {200 {:body string?}}
         :handler logout/handler}})

(def ^:private forgot-password
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?]
         :handler forgot-password/view-handler}
   :post {:responses {200 {:body string?}}
          :middlewares [middlewares/is-setup?]
          :handler forgot-password/action-handler}})

(def ^:private reset-password
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?]
         :handler reset-password/view-handler}
   :post {:responses {200 {:body string?}}
          :middleware [middlewares/is-setup?]
          :handler reset-password/action-handler}})

(def ^:private content
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?
                      middlewares/is-authenticated?
                      middlewares/shape-exists?]
         :handler content/handler}})

(def ^:private add-content
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?
                      middlewares/is-authenticated?
                      middlewares/shape-exists?]
         :handler add-content/handler}})

(def ^:private content-item
  {:get {:responses {200 {:body string?}}
         :middleware [middlewares/is-setup?
                      middlewares/is-authenticated?
                      middlewares/shape-exists?]
         :handler content-item/handler}})

(def ^:private update-content-item-field
  {:post {:responses {200 {:body string?}}
          :middleware [middlewares/is-setup?
                       middlewares/is-authenticated?]
          :handler update-field/handler}})

(def routes
  [["" dashboard]
   ["/setup" setup]
   ["/login" login]
   ["/logout" logout]
   ["/forgot-password" forgot-password]
   ["/reset-password/:token" reset-password]
   ["/content/:identifier" content]
   ["/content/:identifier/add" add-content]
   ["/content/:identifier/item/:id" content-item]
   ["/api/content-item/:id/update-field" update-content-item-field]])
