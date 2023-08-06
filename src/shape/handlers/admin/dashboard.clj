(ns shape.handlers.admin.dashboard
  (:require
   [shape.handlers.utils :refer [->admin-page]]
   [shape.handlers.admin.utils :refer [sidebar]]
   [config :refer [theme]]))

(defn- content [request]
  [:div.content
   [:div.header
    [:h1 "Dashboard"]]])

(defn handler [request]
  (->admin-page
   [:div.container
     (sidebar request)
     (content request)]
   {:css ["admin"]}))
