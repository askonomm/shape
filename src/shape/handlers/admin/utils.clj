(ns shape.handlers.admin.utils
  (:require
   [config :refer [theme]]))

(defn ->sidebar
  []
  [:div.sidebar
   [:div.shapes
    (for [shape (:shapes theme)]
      [:a {:href (str "/admin/content/" (-> shape :identifier name))}
       (:name shape)])]])
