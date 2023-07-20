(ns shape.data
  (:require
    [next.jdbc :as jdbc]
    [dotenv :refer [env]]))

(def db-url (env "DB_URL"))

(def datasource (jdbc/get-datasource db-url))

(defn user-by-token
  [token]
  (jdbc/execute-one! datasource ["SELECT * FROM users WHERE token = ?", token]))

(defn users
  []
  (jdbc/execute! datasource ["SELECT * FROM users"]))
