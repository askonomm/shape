(ns shape.handlers.utils
  (:require
    [hiccup.page :refer [html5]]))

(defn ->redirect [to]
  {:status 302
   :headers {"Location" to}})

(defn ->set-cookie [name value]
  {:headers {"Set-Cookie" (str name "=" value "; max-age=2592000000; path=/")}})

(defn ->page [content opts]
  (html5
    (list 
     [:head 
      [:meta {:charset "utf-8"}] 
      [:title (:title opts)]
      [:link {:rel "stylesheet" :href "/assets/shape/css/general.css"}] 
      (for [css (:css opts)]
        [:link {:rel "stylesheet" :href (str "/assets/shape/css/" css ".css")}])]
     [:body {:class (:body-class opts)}
      content])))
