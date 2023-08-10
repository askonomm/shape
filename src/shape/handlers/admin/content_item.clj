(ns shape.handlers.admin.content-item
  (:require
    [shape.handlers.utils :refer [->redirect ->admin-page]]
    [shape.handlers.admin.utils :refer [sidebar]]
    [shape.data :as data]
    [shape.shapes :as shapes]))

(defn- editable-field [editable identifier content-id]
  [:div.field
   (editable
     {:value (:value (data/content-item-field content-id (name identifier)))
      :content-id content-id})])

(defn- content-editor [fields content-id]
  [:div.content-editor
   (for [field fields]
     (if (vector? field)
       [:div.horizontal-fields
        (for [{:keys [editable identifier]} field]
          (editable-field editable identifier content-id))]
       (let [{:keys [editable identifier]} field]
         (editable-field editable identifier content-id))))])

(defn- content [request]
  (let [shape-identifier (-> request :path-params :identifier)
        shape-identifier-kw (keyword shape-identifier)
        content-id (-> request :path-params :id)
        shape (shapes/first-by-identifier request shape-identifier-kw)
        fields (:fields shape)]
    [:div.content
     [:div.header
      [:h1 (str "Edit "  (:singular-name shape))]
      [:a.button.warning.small
       {:href (str "/admin/content/" shape-identifier "/item/" content-id "/delete")
        :onclick "return confirm('Are you sure you want to delete this item?');"}
       (str "Delete " (:singular-name shape))]]
     (content-editor fields content-id)]))

(defn handler [request]
  (let [shape-identifier (-> request :path-params :identifier)
        shape-identifier-kw (keyword shape-identifier)
        content-id (-> request :path-params :id)
        js (shapes/js-injections-by-identifier request shape-identifier-kw)
        css (shapes/css-injections-by-identifier request shape-identifier-kw)]
    (if (data/content-item-exists? content-id)
      (->admin-page
       [:div.container
        (sidebar request)
        (content request)]
       {:css (concat ["shape/css/admin"] css)
        :js js})
      (->redirect (str "/admin/content/" shape-identifier)))))
