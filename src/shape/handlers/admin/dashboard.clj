(ns shape.handlers.admin.dashboard
  (:require
   [shape.handlers.utils :refer [->admin-page]]))

(defn- view-page [request]
  [:div
   [:a {:href "/admin/logout"} "Log out"]])

(defn handler [request]
  (->admin-page (view-page request)))
