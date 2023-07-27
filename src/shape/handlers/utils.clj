(ns shape.handlers.utils
  (:require
    [ring.middleware.cookies :refer []]
    [hiccup.page :refer [html5]]
    [dotenv :refer [env]]))

(defn ->redirect [to]
  {:status 302
   :headers {"Location" to}})

(defn ->set-cookie [name value]
  {:headers {"Set-Cookie" (str name "=" value "; max-age=259200000; path=/")}})

(defn ->expire-cookie [name]
  {:headers {"Set-Cookie" (str name "=deleted; max-age=0; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT")}})

(defn ->page
  ([content]
   (->page content {}))
  ([content opts]
   (html5
     (list 
      [:head 
       [:meta {:charset "utf-8"}]
       [:meta {:name "viewport"
               :content "width=device-width, initial-scale=1.0"}]
       [:title (:title opts)]
       [:link {:rel "stylesheet" :href "/assets/shape/css/general.css"}] 
       (for [css (:css opts)]
         [:link {:rel "stylesheet" :href (str "/assets/shape/css/" css ".css")}])]
      [:body {:class (:body-class opts)}
       content]))))

(defn smtp-config []
  {:host (env "MAIL_HOST")
   :user (env "MAIL_USER")
   :pass (env "MAIL_PASSWORD")
   :port (Integer/parseInt (env "MAIL_PORT"))
   :tls (some? (env "MAIL_TLS"))
   :ssl (some? (env "MAIL_SSL"))})
