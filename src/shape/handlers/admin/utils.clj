(ns shape.handlers.admin.utils
  (:require
    [shape.shapes :as shapes]))

(defn- list-shapes [request]
  (for [shape (shapes/compute-shapes request)]
    [:div.menu-item
     [:a.button.primary.small {:href (str "/admin/content/" (-> shape :identifier name))
                               :class (when (= (-> request :path-params :identifier) (-> shape :identifier name))
                                        "active")}
      (:name shape)]]))

(defn sidebar
  [request]
  [:div.sidebar
   [:div.logo]
   [:div.menu
    (list-shapes request)]
   [:div.secondary-menu
    [:a.button.secondary.small {:href "/admin/logout"} "Log out"]]])
