(ns shape.handlers.admin.content
  (:require
    [clojure.string :as string]
    [shape.data :as data]
    [shape.handlers.utils :refer [->admin-page]]
    [shape.handlers.admin.utils :refer [sidebar]]
    [shape.shapes :as shapes]))

(defn- content-items [shape-identifier identity-field]
  (for [item (data/content-items shape-identifier)]
    (let [{:keys [field-value] :as a} (data/content-item-field (:id item) (-> identity-field :identifier name))
          value (if (string/blank? field-value)
                  (:placeholder identity-field)
                  field-value)
          viewable (:viewable identity-field)]
      [:div.content-items
       [:a {:href (str "/admin/content/" shape-identifier "/item/" (:id item))}
        [:div.field
         (viewable {:value value})]]])))

(defn- content [request]
  (let [identifier (-> request :path-params :identifier)
        identifier-kw (keyword identifier)
        shape (shapes/first-by-identifier request identifier-kw)
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
     (sidebar request)
     (content request)]
   {:css ["admin"]}))
