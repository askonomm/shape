(ns shape.middlewares)

(defn is-authenticated? [handler]
  (fn [request]
    (if (get-in request [:cookies "_shape_token"])
      (handler request)
      {:status 301
       :headers {"Location" "/admin/login"}
       :body ""})))
