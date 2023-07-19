(ns shape.migrator
  (:require
    [dotenv :refer [env]]
    [ragtime.jdbc :as jdbc]
    [ragtime.repl :as repl]))

(def config 
  {:datastore (jdbc/sql-database {:connection-uri (env "DB_URL")})
   :migrations (jdbc/load-resources "migrations")})

(defn run-migrations []
  (repl/migrate config))

(defn rollback-migrations []
  (repl/rollback config))
   
