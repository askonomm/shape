(ns shape.fields.textarea
  (:require [clojure.string :as string]))

(defn- editable
  "Render a textarea field as an editable view."
  [{:keys [identifier content-id value placeholder auto-size?] :as opts}]
  [:label (:name opts)
   [:div.textarea-field.editable-field
    [:textarea {:placeholder placeholder
                :class (when auto-size?
                         "auto-size")
                :name (name identifier)
                :hx-post (str "/admin/api/content-item/" content-id "/update-field")
                :hx-trigger "input changed delay:250ms"}
     value]]])

(defn- viewable
  "Render a textarea field as a viewable view."
  [{:keys [value]}]
  (when-not (string/blank? value)
    [:div.textarea-field.viewable-field
     [:div.value (->> (string/split value #"\n")
                      (interpose [:br]))]]))

(defn textarea
  "Instance of a textarea field, best suited for long text fields."
  [{:keys [identifier placeholder] :as opts}]
  {:identifier identifier
   :placeholder placeholder
   :inject-css ["shape/css/fields/textarea"]
   :inject-js ["shape/js/fields/textarea"]
   :editable #(editable (merge opts %))
   :viewable #(viewable (merge opts %))})