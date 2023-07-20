(ns shape.data
  (:require
    [next.jdbc :as jdbc]
    [dotenv :refer [env]]))

(def db-url (env "DB_URL"))

(def datasource (jdbc/get-datasource db-url))

(defn user-by-token
  [token]
  (let [sql "SELECT * FROM users WHERE token = ?"]
    (jdbc/execute-one! datasource [sql token])))

(defn users
  []
  (let [sql "SELECT * FROM users"]
    (jdbc/execute! datasource [sql])))

(defn create-user!
  [{:keys [email password role]}]
  (let [sql "INSERT INTO users (email, password, role, created_at) VALUES (?, ?, ?, ?)"
        result (jdbc/execute-one! datasource [sql email password role] {:return-keys true})
        kw (keyword "last_insert_rowid()")]
    (kw result)))

(defn set-user-token!
  [user-id token]
  (let [sql "UPDATE users SET token = ? WHERE id = ?"]
    (jdbc/execute-one! datasource [sql token user-id])))
