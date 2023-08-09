(ns shape.handlers.admin.main
  (:require
    [shape.shapes :as shapes]
    [shape.handlers.utils :refer [->redirect]]))

(defn handler [request]
  (let [shapes (shapes/compute-shapes request)]
    (if-let [shape-identifier (-> shapes first :identifier name)]
      (->redirect (str "/admin/content/" shape-identifier))
      (->redirect (str "/admin/logout")))))
