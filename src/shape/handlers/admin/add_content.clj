(ns shape.handlers.admin.add-content
  (:require
   [shape.data :as data]
   [shape.handlers.utils :refer [->redirect]]))

(defn handler [request]
  (let [identifier (-> request :path-params :identifier)
        content-id (data/create-content-item! {:shape-identifier identifier})]
    (->redirect (str "/admin/content/" identifier "/item/" content-id))))
