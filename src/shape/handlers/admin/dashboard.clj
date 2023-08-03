(ns shape.handlers.admin.dashboard
  (:require
   [shape.handlers.utils :refer [->admin-page]]
   [shape.handlers.admin.utils :refer [sidebar]]
   [config :refer [theme]]))

(defn- view-page [request]
  [:div
   [:a {:href "/admin/logout"} "Log out"]])

(defn handler [request]
  (->admin-page
   (list
     (sidebar request)
     (view-page request))
   {:css ["admin"]}))
