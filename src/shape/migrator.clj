(ns shape.migrator
  (:require
    [shape.data :refer [db-url]]
    [ragtime.jdbc :as jdbc]
    [ragtime.repl :as repl]))

(def config 
  {:datastore (jdbc/sql-database {:connection-uri (db-url)})
   :migrations (jdbc/load-resources "migrations")})

(defn run-migrations []
  (repl/migrate config))

(defn rollback-migrations []
  (repl/rollback config))
   
