(ns shape.handlers.admin.content-item
  (:require
    [shape.handlers.utils :refer [->redirect ->admin-page]]
    [shape.handlers.admin.utils :refer [sidebar]]
    [shape.data :as data]
    [shape.shapes :as shapes]))

(defn- content [request]
  (let [shape-identifier (-> request :path-params :identifier)
        shape-identifier-kw (keyword shape-identifier)
        content-id (-> request :path-params :id)
        shape (shapes/first-by-identifier request shape-identifier-kw)
        fields (:fields shape)]
    [:div.content
     [:div.inner-content
      [:div.header
       [:h1 (str "Edit "  (:singular-name shape))]
       [:a.button.warning.small
        {:href (str "/admin/content/" shape-identifier "/item/" content-id "/delete")
         :onclick "return confirm('Are you sure you want to delete this item?');"}
        (str "Delete " (:singular-name shape))]]
      [:div.content-editor
       (for [field fields]
         (if (vector? field)
           [:div.horizontal-fields
            (for [{:keys [editable identifier]} field]
              [:div.field
               (editable
                 {:value (:value (data/content-item-field content-id (name identifier)))
                  :content-id content-id})])]
           (let [{:keys [editable identifier]} field]
             [:div.field
              (editable
                {:value (:value (data/content-item-field content-id (name identifier)))
                 :content-id content-id})])))]]]))

(defn handler [request]
  (let [shape-identifier (-> request :path-params :identifier)
        shape-identifier-kw (keyword shape-identifier)
        content-id (-> request :path-params :id)]
    (if (data/content-item-exists? content-id)
      (->admin-page
       [:div.container
        (sidebar request)
        (content request)]
       {:css (concat ["shape/css/admin"]
                     (shapes/css-injections-by-identifier request shape-identifier-kw))
        :js (shapes/js-injections-by-identifier request shape-identifier-kw)})
      (->redirect (str "/admin/content/" shape-identifier)))))
