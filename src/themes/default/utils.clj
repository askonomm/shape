(ns themes.default.utils
  (:import (java.text SimpleDateFormat)
           (java.util Date)))

(defn- date-str->epoch [date format]
  (.getTime (.parse (SimpleDateFormat. format) date)))

(defn sort-by-published-at [items]
  (sort-by
    (fn [item]
      (if-let [field (->> item :fields (filter #(= (:identifier %) "published-at")) first)]
        (date-str->epoch (:field-value field) "yyyy-MM-dd")
        (.getTime (Date.))))
    #(> %1 %2)
    items))