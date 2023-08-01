(ns shape.handlers.admin.content-item
  (:require
   [shape.handlers.utils :refer [->redirect ->admin-page]]
   [shape.handlers.admin.utils :refer [->sidebar]]
   [shape.data :as data]
   [config :refer [theme]]))

(defn- handler-page [request]
  (let [shape-identifier-kw (-> request :path-params :identifier keyword)
        content-id (-> request :path-params :id)
        fields (->> theme :shapes (filter #(= (:identifier %) shape-identifier-kw)) first :fields)]
    (for [{:keys [renderer identifier]} fields]
      (renderer {:value (:value (data/content-item-field content-id (name identifier)))
                 :content-id (Integer/parseInt content-id)}))))

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
