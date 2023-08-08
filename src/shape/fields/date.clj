(ns shape.fields.date)

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
  [{:keys [value]}]
  [:div.date-field.viewable-field
   [:span.value value]])

(defn date
  "Instance of a date field."
  [{:keys [identifier placeholder] :as opts}]
  {:identifier identifier
   :placeholder placeholder
   :editable #(editable (merge opts %))
   :viewable #(viewable (merge opts %))})
