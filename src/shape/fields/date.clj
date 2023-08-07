(ns shape.fields.date)

(defn date
  "Instance of a date field."
  [{:keys [identifier placeholder] :as opts}]
  {:identifier identifier
   :placeholder placeholder
   :editable nil #_(editable (merge opts %))
   :viewable nil #_(viewable (merge opts %))})