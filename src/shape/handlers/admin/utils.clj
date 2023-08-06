(ns shape.handlers.admin.utils
  (:require
    [shape.shapes :as shapes]))

(defn sidebar
  [request]
  [:div.sidebar
   [:div.menu
    [:a {:href "/admin"
         :class (when (empty? (:path-params request))
                  "active")} "Dashboard"]
    (for [shape (shapes/shapes request)]
      [:a {:href (str "/admin/content/" (-> shape :identifier name))
           :class (when (= (-> request :path-params :identifier) (-> shape :identifier name))
                    "active")}
       (:name shape)])]
   [:div.secondary-menu
    [:a {:href "/admin/logout"} "Log out"]]])
