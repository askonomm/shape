(ns shape.fields.text)

(defn- editable
  "Render a text field as an editable view."
  [{:keys [identifier content-id value prefix suffix placeholder] :as opts}]
  [:label (:name opts)
   [:div.text-field.editable-field
    {:class [(when suffix "suffix")
             (when prefix "prefix")]}
    (when prefix
      [:span.prefix prefix])
    [:input {:type "text"
             :value value
             :placeholder placeholder
             :name (name identifier)
             :hx-post (str "/admin/api/content-item/" content-id "/update-field")
             :hx-trigger "keyup changed delay:250ms"}]
    (when suffix
      [:span.suffix suffix])]])

(defn- viewable
  "Render a text field as a viewable view."
  [{:keys [value prefix suffix]}]
  [:div.text-field.viewable-field
   {:class [(when suffix "suffix")
            (when prefix "prefix")]}
   (when prefix
     [:span.prefix prefix])
   [:span.value value]
   (when suffix
     [:span.suffix suffix])])

(defn text
  "Instance of a text field, best suited for short text fields."
  [{:keys [identifier placeholder] :as opts}]
  {:identifier identifier
   :placeholder placeholder
   :inject-css ["shape/css/fields/text"]
   :editable #(editable (merge opts %))
   :viewable #(viewable (merge opts %))})