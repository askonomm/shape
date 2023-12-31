(ns shape.handlers.admin.utils
  (:require
    [shape.shapes :as shapes]))

(defn- list-shapes [request]
  (for [shape (shapes/compute-shapes request)]
    [:div.menu-item
     [:a {:href (str "/admin/content/" (-> shape :identifier name))
          :class (when (= (-> request :path-params :identifier) (-> shape :identifier name))
                   "active")}
      (:name shape)]]))

(defn sidebar
  [request]
  [:div.sidebar
   [:div
    [:div.logo]
    [:div.menu
     (list-shapes request)
     [:a {:href "/admin/logout"} "Log out"]]]])

