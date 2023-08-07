(ns shape.shapes
  (:require
    [config :refer [theme]]
    [shape.utils :as utils]))

(defn- compose-shape-data [request]
  {:site/url (utils/request->url request)})

(defn compute-shapes [request]
  (->> (:shapes theme)
       (map (fn [shape]
              (if (fn? shape)
                (-> request
                    compose-shape-data
                    shape)
                shape)))))

(defn first-by-identifier [request identifier]
  (->> (compute-shapes request)
       (filter #(= (:identifier %) identifier))
       first))

(defn js-injections-by-identifier [request identifier]
  (->> (compute-shapes request)
       (filter #(= (:identifier %) identifier))
       first
       :fields
       (map :inject-js)
       (remove nil?)
       flatten
       distinct
       (into [])))

(defn css-injections-by-identifier [request identifier]
  (->> (compute-shapes request)
       (filter #(= (:identifier %) identifier))
       first
       :fields
       (map :inject-css)
       (remove nil?)
       flatten
       distinct
       (into [])))