(ns askonomm.data
  (:require
    [next.jdbc :as jdbc]
    [dotenv :refer [env]]))

(def db-url (env "DB_URL"))
(def datasource (jdbc/get-datasource db-url))


