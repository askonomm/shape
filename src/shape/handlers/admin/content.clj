(ns shape.handlers.admin.content
  (:require
   [shape.handlers.utils :refer [->admin-page]]
   [shape.handlers.admin.utils :refer [->sidebar]]
   [config :refer [theme]]))

(defn- handler-page [request]
  (let [identifier (-> request :path-params :identifier)
        identifier-kw (keyword identifier)
        shape (->> theme :shapes (filter #(= (:identifier %) identifier-kw)) first)]
    [:div
     [:h2 (:name shape)]
     [:a {:href (str "/admin/content/" identifier "/add")}
      (str "Add " (:singular-name shape))]]))

(defn handler [request]
  (->admin-page
   (list
    (->sidebar)
    (handler-page request))
   {:css ["admin"]}))
