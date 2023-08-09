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
     [:div.header
      [:h1 (str "Edit "  (:singular-name shape))]
      [:a.button.secondary.small
       {:href (str "/admin/content/" shape-identifier "/item/" content-id "/delete")
        :onclick "return confirm('Are you sure you want to delete this item?');"}
       (str "Delete " (:singular-name shape))]]
     [:div.content-editor
      (for [{:keys [editable identifier]} fields]
        [:div.field
         (editable
           (data/content-item-field content-id (name identifier)))])]]))

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
