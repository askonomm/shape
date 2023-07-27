(ns shape.handlers.admin.logout
  (:require
   [shape.utils :refer [->merge]]
   [shape.handlers.utils :refer [->redirect ->expire-cookie]]))

(defn handler [_]
  (->merge (->redirect "/admin")
           (->expire-cookie "_shape_token")))
