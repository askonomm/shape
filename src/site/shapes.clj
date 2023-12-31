(ns site.shapes
  (:require
    [shape.fields.text :refer [text]]
    [shape.fields.textarea :refer [textarea]]
    [shape.fields.date :refer [date]]
    [shape.fields.select :refer [select]]
    [site.utils :as utils]))

(defn blog-post [{:site/keys [url]}]
  {:identifier :post
   :name "Blog Posts"
   :singular-name "Blog Post"
   :admin-list-view-field [:title :status :published-at]
   :admin-list-view-sort-fn utils/sort-by-published-at
   :fields [(text {:identifier :title
                   :placeholder "Untitled ..."
                   :name "Title"})
            (text {:identifier :url-slug
                   :name "URL Slug"
                   :prefix (str url "/blog/")})
            (textarea {:identifier :content
                       :name "Content"
                       :auto-size? true})
            [(select {:identifier :status
                      :name "Status"
                      :options ["Draft" "Published"]})
             (date {:identifier :published-at
                    :name "Published At"
                    :format "MMMM dd, yyyy"})]]})

(defn page [{:site/keys [url]}]
  {:identifier :page
   :name "Pages"
   :singular-name "Page"
   :admin-list-view-field :title
   :fields [(text {:identifier :title
                   :placeholder "Untitled ..."
                   :name "Title"})
            (text {:identifier :url-slug
                   :name "URL Slug"
                   :prefix (str url "/")})
            (textarea {:identifier :content
                       :name "Content"
                       :auto-size? true})]})
