(ns shape.shapes
  (:require
    [site.config :refer [config]]
    [shape.utils :as utils]))

(defn- compose-shape-data [request]
  {:site/url (utils/request->url request)})

(defn compute-shapes [request]
  (->> (:shapes config)
       (map (fn [shape]
              (if (fn? shape)
                (-> request
                    compose-shape-data
                    shape)
                shape)))))

; todo: use transducers
(defn first-by-identifier [request identifier]
  (->> (compute-shapes request)
       (filter #(= (:identifier %) identifier))
       first))

; todo: use transducers
(defn js-injections-by-identifier [request identifier]
  (->> (compute-shapes request)
       (filter #(= (:identifier %) identifier))
       first
       :fields
       flatten
       (map :inject-js)
       (remove nil?)
       flatten
       distinct
       (into [])))

; todo: use transducers
(defn css-injections-by-identifier [request identifier]
  (->> (compute-shapes request)
       (filter #(= (:identifier %) identifier))
       first
       :fields
       flatten
       (map :inject-css)
       (remove nil?)
       flatten
       distinct
       (into [])))