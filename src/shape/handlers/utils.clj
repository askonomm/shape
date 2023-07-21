(ns shape.handlers.utils)

(defn ->redirect [to]
  {:status 302
   :headers {"Location" to}})

(defn ->set-cookie [name value]
  {:headers {"Set-Cookie" (str name "=" value "; max-age=2592000000; path=/")}})