(ns shape.handlers.admin.content
  (:require
    [clojure.string :as string]
    [shape.data :as data]
    [shape.handlers.utils :refer [->admin-page]]
    [shape.handlers.admin.utils :refer [sidebar]]
    [shape.shapes :as shapes]))

(defn- content-items
  [{:keys [shape-identifier list-view-fields list-view-sort-fn]}]
  (let [content-items (if (fn? list-view-sort-fn)
                        (-> (data/content-items shape-identifier)
                            list-view-sort-fn)
                        (data/content-items shape-identifier))]
    [:div.content-items
     (for [item content-items]
       [:a {:href (str "/admin/content/" shape-identifier "/item/" (:id item))}
        (for [field list-view-fields]
          (let [{:keys [value]} (->> item :fields (filter #(= (:identifier %) (-> field :identifier name))) first)
                value (if (string/blank? value)
                        (or (:placeholder field) "")
                        value)
                viewable (:viewable field)]
             [:div.field
              (viewable {:value value})]))])]))

(defn- content [request]
  (let [shape-identifier (-> request :path-params :identifier)
        shape-identifier-kw (keyword shape-identifier)
        shape (shapes/first-by-identifier request shape-identifier-kw)
        list-view-fields-identifiers (if (vector? (:admin-list-view-field shape))
                                       (:admin-list-view-field shape)
                                       [(:admin-list-view-field shape)])
        list-view-fields-filter-fn (fn [field]
                                     (some #(= (:identifier field) %) list-view-fields-identifiers))
        list-view-fields (->> shape :fields flatten (filter list-view-fields-filter-fn))
        list-view-sort-fn (:admin-list-view-sort-fn shape)]
    [:div.content
     [:div.header
      [:h1 (:name shape)]
      [:a.button.primary.small {:href (str "/admin/content/" shape-identifier "/add")}
       (str "Add " (:singular-name shape))]]
     (content-items {:shape-identifier shape-identifier
                     :list-view-fields list-view-fields
                     :list-view-sort-fn list-view-sort-fn})]))

(defn handler [request]
  (let [shape-identifier (-> request :path-params :identifier)
        shape-identifier-kw (keyword shape-identifier)]
    (->admin-page
      [:div.container
       (sidebar request)
       (content request)]
      {:css (concat ["shape/css/admin"]
                    (shapes/css-injections-by-identifier request shape-identifier-kw))})))
