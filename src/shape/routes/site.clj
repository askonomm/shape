(ns shape.routes.site
  (:require
    [shape.handlers.site.home :as handlers.site.home]))

(def ^:private home 
  {:get {:responses {200 {:body string?}}
              :handler handlers.site.home/handler}})

(def routes
  [["" home]])      
        
