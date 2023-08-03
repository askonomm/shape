(ns themes.default.shapes
  (:require
   [shape.fields :as fields]
   [shape.utils :as utils]))

(defn- blog-post []
  {:identifier :post
   :name "Blog Posts"
   :singular-name "Blog Post"
   :identity-field :title
   :fields [(fields/text {:identifier :title
                          :placeholder "Untitled ..."
                          :name "Post Title"})
            (fields/text {:identifier :url-slug
                          :name "URL Slug"
                          :prefix (str utils/*url* "/blog/")})]})

(defn- page []
  {:identifier :page
   :name "Pages"
   :singular-name "Page"
   :identity-field :title
   :fields [(fields/text {:identifier :title
                          :placeholder "Untitled ..."
                          :name "Page Title"})
            (fields/text {:identifier :url-slug
                          :name "URL Slug"
                          :prefix (str utils/*url* "/")})]})

(def shapes
  [(blog-post)
   (page)])
