(ns themes.default.utils
  (:import (java.text SimpleDateFormat)))

(defn- date-str->epoch [date format]
  (.getTime (.parse (SimpleDateFormat. format) date)))

(defn sort-by-published-at [items]
  (sort-by
    (fn [item]
      (let [field (->> item :fields (filter #(= (:field-identifier %) "published-at")) first)]
        (date-str->epoch (:field-value field) "yyyy-MM-dd")))
    #(> %1 %2)
    items))