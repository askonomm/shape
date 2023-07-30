(ns themes.default.shapes
  (:require
   [shape.fields :as fields]))

(def ^:private blog-post
  {:identifier :post
   :name "Blog Posts"
   :singular-name "Blog Post"
   :fields [(fields/text {:identifier :title
                          :name "Post Title"})]})

(def shapes
  [blog-post])
