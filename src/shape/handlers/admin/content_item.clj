(ns shape.handlers.admin.content-item
  (:require
   [shape.handlers.utils :refer [->redirect ->admin-page]]
   [shape.handlers.admin.utils :refer [->sidebar]]
   [shape.data :as data]))

(defn- handler-page [request]
  [:div "hello"])

(defn handler [request]
  (let [shape-identifier (-> request :path-params :identifier)
        content-id (-> request :path-params :id)]
    (if (data/content-item-exists? content-id)
      (->admin-page
       (list
        (->sidebar)
        (handler-page request))
       {:css ["admin"]})
      (->redirect (str "/admin/content/" shape-identifier)))))
