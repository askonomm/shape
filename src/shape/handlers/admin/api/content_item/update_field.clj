(ns shape.handlers.admin.api.content-item.update-field
  (:require
    [shape.data :as data]
    [shape.handlers.utils :refer [->json]]))

(defn handler [request]
  (let [content-id (-> request :path-params :id)
        field-identifier (-> request :form-params keys first)
        field-value (-> request :form-params vals first)]
    (data/set-content-item-field! content-id field-identifier field-value)
    (->json {:success true})))
