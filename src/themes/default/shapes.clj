(ns themes.default.shapes
  (:require
   [shape.fields :as fields]
   [shape.utils :as utils]))

; the only reason `delay` is used here is that
; `utils/*url*` will not be defined until runtime.
; You don't otherwise need `delay` here.
(def blog-post
  (delay {:identifier :post
          :name "Blog Posts"
          :singular-name "Blog Post"
          :identity-field :title
          :fields [(fields/text {:identifier :title
                                 :placeholder "Untitled ..."
                                 :name "Post Title"})
                   (fields/text {:identifier :url-slug
                                 :name "URL Slug"
                                 :prefix (str utils/*url* "/")})]}))

(def shapes
  [@blog-post])
