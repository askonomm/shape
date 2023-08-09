(ns shape.fields.select
  (:require
    [clojure.string :as string]))

(defn- editable
  "Render a select field as an editable view."
  [{:keys [identifier content-id options] :as opts}]
  [:label (:name opts)
   [:div.select-field.editable-field
    [:select {:name (name identifier)
              :hx-post (str "/admin/api/content-item/" content-id "/update-field")
              :hx-trigger "input changed delay:250ms"}
     (for [option options]
       [:option {:value option} option])]]])

(defn- viewable
  "Render a select field as a viewable view."
  [{:keys [value]}]
  (when-not (string/blank? value)
    [:div.select-field.viewable-field
     value]))

(defn select
  "Instance of a select field."
  [{:keys [identifier placeholder] :as opts}]
  {:identifier identifier
   :placeholder placeholder
   :inject-css ["shape/css/fields/select"]
   :editable #(editable (merge opts %))
   :viewable #(viewable (merge opts %))})