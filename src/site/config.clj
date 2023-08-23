(ns site.config
  (:require
   [site.shapes :as shapes]))

(def config
  {:shapes [shapes/blog-post
            shapes/page]})
