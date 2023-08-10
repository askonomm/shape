(ns shape.fields.date
  (:require
    [clojure.string :as string])
  (:import
    (java.text SimpleDateFormat)
    (java.util Date)))

(defn- editable
  "Render a date field as an editable view."
  [{:keys [identifier content-id value] :as opts}]
  [:label (:name opts)
   [:div.date-field.editable-field
    [:input {:type "date"
             :value value
             :name (name identifier)
             :hx-post (str "/admin/api/content-item/" content-id "/update-field")
             :hx-trigger "input changed delay:250ms"}]]])

(defn- viewable
  "Render a date field as a viewable view."
  [{:keys [value format]}]
  (when-not (string/blank? value)
    (let [epoch (.getTime (.parse (SimpleDateFormat. "yyyy-MM-dd") value))
          sdf (SimpleDateFormat. (or format "yyyy-MM-dd"))
          date (.format sdf (Date. epoch))]
      [:div.date-field.viewable-field
       [:span.value
        [:i.fa.fa-calendar]
        date]])))

(defn date
  "Instance of a date field."
  [{:keys [identifier placeholder] :as opts}]
  {:identifier identifier
   :placeholder placeholder
   :inject-css ["shape/css/fields/date"]
   :editable #(editable (merge opts %))
   :viewable #(viewable (merge opts %))})
