(ns shape.handlers.admin.content
  (:require
    [clojure.string :as string]
    [shape.data :as data]
    [shape.handlers.utils :refer [->admin-page]]
    [shape.handlers.admin.utils :refer [sidebar]]
    [shape.shapes :as shapes]))

(defn- content-items [shape-identifier admin-list-view-field]
  [:div.content-items
   (for [item (data/content-items shape-identifier)]
     (let [{:keys [field-value] :as a} (data/content-item-field (:id item) (-> admin-list-view-field :identifier name))
           value (if (string/blank? field-value)
                   (:placeholder admin-list-view-field)
                   field-value)
           viewable (:viewable admin-list-view-field)]
       [:a {:href (str "/admin/content/" shape-identifier "/item/" (:id item))}
        [:div.field
         (viewable {:value value})]]))])

(defn- content [request]
  (let [identifier (-> request :path-params :identifier)
        identifier-kw (keyword identifier)
        shape (shapes/first-by-identifier request identifier-kw)
        admin-list-view-field-identifier (:admin-list-view-field shape)
        admin-list-view-field (->> shape :fields (filter #(= (:identifier %) admin-list-view-field-identifier)) first)]
    [:div.content
     [:div.header
      [:h1 (:name shape)]
      [:a.button.primary.small {:href (str "/admin/content/" identifier "/add")}
       (str "Add " (:singular-name shape))]]
     (content-items identifier admin-list-view-field)]))

(defn handler [request]
  (->admin-page
    [:div.container
     (sidebar request)
     (content request)]
   {:css ["admin"]}))
