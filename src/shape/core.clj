(ns shape.core
  (:require
    [ring.adapter.jetty :refer [run-jetty]]
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

(prn (into [] (concat routes.admin/routes routes.site/routes)))

(def app
  (ring/ring-handler
    (ring/router
      [["/" routes.site/routes]
       ["/admin" routes.admin/routes]]
      {:data {:coercion reitit.coercion.spec/coercion
              :muuntaja m/instance
              :middleware [parameters/parameters-middleware
                           rrc/coerce-request-middleware
                           muuntaja/format-response-middleware
                           rrc/coerce-response-middleware]}})))
      
(defn run [_]
  (if (nil? (env "DB_URL"))
    (println "DB_URL environment variable is not set, cannot continue.")
    (do (migrator/run-migrations)
        (run-jetty app {:port 3999
                        :join? false}))))
  
