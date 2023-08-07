(ns themes.default.shapes
  (:require
   [shape.fields.text :refer [text]]
   [shape.fields.textarea :refer [textarea]]))

(defn blog-post [{:site/keys [url]}]
  {:identifier :post
   :name "Blog Posts"
   :singular-name "Blog Post"
   :admin-list-view-field :title
   :fields [(text {:identifier :title
                   :placeholder "Untitled ..."
                   :name "Post Title"})
            (text {:identifier :url-slug
                   :name "URL Slug"
                   :prefix (str url "/blog/")})
            (textarea {:identifier :content
                       :name "Content"
                       :auto-size? true})]})

(defn page [{:site/keys [url]}]
  {:identifier :page
   :name "Pages"
   :singular-name "Page"
   :admin-list-view-field :title
   :fields [(text {:identifier :title
                   :placeholder "Untitled ..."
                   :name "Page Title"})
            (text {:identifier :url-slug
                   :name "URL Slug"
                   :prefix (str url "/")})]})
