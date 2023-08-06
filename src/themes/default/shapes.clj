(ns themes.default.shapes
  (:require
   [shape.fields.text :refer [text]]
   [shape.utils :as utils]))

(defn blog-post [request]
  {:identifier :post
   :name "Blog Posts"
   :singular-name "Blog Post"
   :identity-field :title
   :fields [(text {:identifier :title
                   :placeholder "Untitled ..."
                   :name "Post Title"
                   :prefix "asd"
                   :suffix "asdasd"})
            (text {:identifier :url-slug
                   :name "URL Slug"
                   :prefix (str (utils/request->url request)"/blog/")})]})

(defn page [request]
  {:identifier :page
   :name "Pages"
   :singular-name "Page"
   :identity-field :title
   :fields [(text {:identifier :title
                   :placeholder "Untitled ..."
                   :name "Page Title"})
            (text {:identifier :url-slug
                   :name "URL Slug"
                   :prefix (str (utils/request->url request) "/")})]})
