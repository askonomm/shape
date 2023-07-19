(ns askonomm.shape
  (:require
    [ring.adapter.jetty :refer [run-jetty]]
    [reitit.ring :as ring]
    [reitit.coercion.spec]
    [reitit.ring.coercion :as rrc]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.ring.middleware.parameters :as parameters]
    [muuntaja.core :as m]
    [askonomm.routes.site :as routes.site])
  (:gen-class))

(def app
  (ring/ring-handler
    (ring/router
      (concat routes.site/routes)
      {:data {:coercion reitit.coercion.spec/coercion
              :muuntaja m/instance
              :middleware [parameters/parameters-middleware
                           rrc/coerce-request-middleware
                           muuntaja/format-response-middleware
                           rrc/coerce-response-middleware]}})))
      
(defn run
  "I don't do a whole lot ... yet."
  [_]
  (run-jetty app {:port 3999
                  :join? false}))
  
