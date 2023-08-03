(ns shape.shapes
  (:require
    [config :refer [theme]]))

(defn- compute-shapes [request]
  (->> (:shapes theme)
       (map (fn [shape]
              (if (fn? shape)
                (shape request)
                shape)))))
(defn first-by-identifier [request identifier]
  (->> (compute-shapes request)
       (filter #(= (:identifier %) identifier))
       first))

(defn shapes [request]
  (compute-shapes request))