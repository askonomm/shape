(ns themes.default.shapes
  (:require
   [shape.fields :as fields]))

(def ^:private blog-post
  {:identifier :post
   :name "Blog Posts"
   :singular-name "Blog Post"
   :identity-field :title
   :fields [(fields/text {:identifier :title
                          :placeholder "Untitled ..."
                          :name "Post Title"})
            (fields/text {:identifier :url-slug
                          :name "URL Slug"
                          :prefix "https://google.com/"})]})

(def shapes
  [blog-post])
