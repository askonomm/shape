(ns shape.handlers.admin.dashboard
  (:require
   [shape.handlers.utils :refer [->page]]))

(defn- view-page [request]
  [:div
   [:a {:href "/admin/logout"} "Log out"]])

(defn handler [request]
  {:status 200
   :body (->page (view-page request))})
