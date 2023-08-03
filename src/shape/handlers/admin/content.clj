(ns shape.handlers.admin.content
  (:require
    [clojure.string :as string]
    [shape.data :as data]
    [shape.handlers.utils :refer [->admin-page]]
    [shape.handlers.admin.utils :refer [->sidebar]]
    [config :refer [theme]]))

(defn- content-items [shape-identifier identity-field]
  (for [item (data/content-items shape-identifier)]
    (let [field (data/content-item-field (:id item) (:identifier identity-field))
          value (if (string/blank? (:value field))
                  (:placeholder identity-field)
                  (:value field))
          viewable (:viewable identity-field)]
      [:div.content-items
       [:a {:href (str "/admin/content/" shape-identifier "/item/" (:id item))}
        [:div.field
         (viewable {:value value})]]])))

(defn- handler-page [request]
  (let [identifier (-> request :path-params :identifier)
        identifier-kw (keyword identifier)
        shape (->> theme :shapes (filter #(= (:identifier %) identifier-kw)) first)
        identity-field-identifier (:identity-field shape)
        identity-field (->> shape :fields (filter #(= (:identifier %) identity-field-identifier)) first)]
    [:div.content
     [:div.header
      [:h1 (:name shape)]
      [:a.button.primary.small {:href (str "/admin/content/" identifier "/add")}
       (str "Add " (:singular-name shape))]]
     (content-items identifier identity-field)]))

(defn handler [request]
  (->admin-page
    [:div.container
     (->sidebar)
     (handler-page request)]
   {:css ["admin"]}))
