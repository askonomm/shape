(ns shape.core
  (:require
    [ring.adapter.jetty :refer [run-jetty]]
    [ring.middleware.reload :refer [wrap-reload]]
    [ring.middleware.cookies :refer [wrap-cookies]]
    [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
    [ring.middleware.session :refer [wrap-session]]
    [ring.middleware.resource :refer [wrap-resource]]
    [reitit.ring :as ring]
    [reitit.coercion.spec]
    [reitit.ring.coercion :as rrc]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.ring.middleware.parameters :as parameters]
    [muuntaja.core :as m]
    [dotenv :refer [env]]
    [shape.routes.site :as routes.site]
    [shape.routes.admin :as routes.admin]
    [shape.migrator :as migrator])
  (:gen-class))

(def app
  (ring/ring-handler
    (ring/router
      [["/" routes.site/routes]
       ["/admin" routes.admin/routes]]
      {:data {:coercion reitit.coercion.spec/coercion
              :muuntaja m/instance
              :middleware [parameters/parameters-middleware
                           rrc/coerce-request-middleware
                           muuntaja/format-middleware
                           rrc/coerce-response-middleware]}})
    {:middleware [wrap-session
                  wrap-anti-forgery
                  wrap-cookies]}))

(def handler
  (-> app
      (wrap-resource "public")))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_]
  (do (migrator/run-migrations)
      (run-jetty (wrap-reload #'handler)
                 {:port (if (env "PORT")
                          (Integer/parseInt (env "PORT"))
                          3999)
                  :join? false})))

(defn -main [& args]
  (run args))
