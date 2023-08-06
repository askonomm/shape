(ns shape.handlers.admin.utils
  (:require
    [shape.shapes :as shapes]))

(defn- list-shapes [request]
  (for [shape (shapes/compute-shapes request)]
    [:a {:href (str "/admin/content/" (-> shape :identifier name))
         :class (when (= (-> request :path-params :identifier) (-> shape :identifier name))
                  "active")}
     (:name shape)]))

(defn sidebar
  [request]
  [:div.sidebar
   [:div.menu
    [:a {:href "/admin"
         :class (when (empty? (:path-params request))
                  "active")} "Dashboard"]
    (list-shapes request)]
   [:div.secondary-menu
    [:a {:href "/admin/logout"} "Log out"]]])
