(ns shape.handlers.admin.utils
  (:require
    [shape.shapes :as shapes]))

(defn sidebar
  [request]
  [:div.sidebar
   [:div.shapes
    (for [shape (shapes/shapes request)]
      [:a {:href (str "/admin/content/" (-> shape :identifier name))
           :class (when (= (-> request :path-params :identifier) (-> shape :identifier name))
                    "active")}
       (:name shape)])]])
