(ns shape.fields)

(defn- text-editable
  "Render a text field as an editable view."
  [{:keys [identifier name content-id value prefix suffix]}]
  [:label name
   [:div.text-field
    (when prefix
      [:span.prefix prefix])
    [:input {:type "text"
             :value value
             :name (str identifier)
             :hx-post (str "/admin/api/content-item/" content-id "/update-field")
             :hx-trigger "keyup changed delay:250ms"}]
    (when suffix
      [:span.suffix suffix])]])

(defn- text-viewable
  "Render a text field as a viewable view."
  [{:keys [value prefix suffix]}]
  [:div.text-field
   (when prefix
     [:span.prefix prefix])
   [:span value]
   (when suffix
     [:span.suffix suffix])])

(defn text
  "Instance of a text field, best suited for short text fields."
  [{:keys [identifier name placeholder] :as opts}]
  {:identifier identifier
   :name name
   :placeholder placeholder
   :editable #(text-editable (merge opts %))
   :viewable #(text-viewable (merge opts %))})
