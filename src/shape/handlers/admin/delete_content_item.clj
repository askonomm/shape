(ns shape.handlers.admin.delete-content-item
  (:require
    [shape.data :as data]
    [shape.handlers.utils :refer [->redirect]]))

(defn handler [request]
  (let [shape-identifier (-> request :path-params :identifier)
        content-id (-> request :path-params :id)]
    (if (data/content-item-exists? content-id)
      (do (data/delete-content-item! content-id)
          (->redirect (str "/admin/content/" shape-identifier)))
      (->redirect (str "/admin/content/" shape-identifier)))))
