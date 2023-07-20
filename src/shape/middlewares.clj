(ns shape.middlewares
  (:require
    [shape.data :as data]))

(defn is-authenticated?
  "Check if the user has the authenticated token present, and
  that it matches the token in the DB. Otherwise, redirect to the
  Login page."
  [handler]
  (fn [request]
    (if-let [token (get-in request [:cookies "_shape_token"])]
      (if (data/user-by-token token)
        (handler request)
        {:status 302
         :headers {"Location" "/admin/login?err=invalid-token"}
         :body ""})
      {:status 302
       :headers {"Location" "/admin/login?err=no-token"}
       :body ""})))

(defn is-setup?
  "Check that the site is set up."
  [handler]
  (fn [request]
    (if (empty? (data/users))
      {:status 302
       :headers {"Location" "/admin/setup"}
       :body ""}
      (handler request))))

(defn isnt-setup?
  "Check that the site is not set up."
  [handler]
  (fn [request]
    (if (empty? (data/users))
      (handler request)
      {:status 302
       :headers {"Location" "/admin/setup"}
       :body ""})))