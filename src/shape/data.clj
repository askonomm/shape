(ns shape.data
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]
    [dotenv :refer [env]]
    [crypto.password.bcrypt :as password]))

(def db-url (env "DB_URL"))

(def datasource (jdbc/get-datasource db-url))

(defn- query-one!
  ([data]
   (query-one! data false))
  ([data return-keys?]
   (jdbc/execute-one! datasource data {:return-keys return-keys?
                                       :builder-fn rs/as-unqualified-kebab-maps})))

(defn user-by-token
  [token]
  (let [sql "SELECT * FROM users WHERE token = ?"]
    (query-one! [sql token])))

(defn user-by-email
  [email]
  (let [sql "SELECT * FROM users WHERE email = ?"]
    (query-one! [sql email])))

(defn users
  []
  (let [sql "SELECT * FROM users"]
    (query-one! [sql])))

(defn create-user!
  [{:keys [email password role]}]
  (let [sql "INSERT INTO users (email, password, role, created_at) VALUES (?, ?, ?, ?)"
        result (query-one! [sql email (password/encrypt password) role] true)
        kw (keyword "last-insert-rowid()")]
    (kw result)))

(defn set-user-token!
  [user-id token]
  (let [sql "UPDATE users SET token = ? WHERE id = ?"]
    (query-one! [sql token user-id])))

(defn user-exists-by-email?
  [email]
  (boolean (user-by-email email)))

(defn user-authenticates?
  [email password]
  (when-let [user (user-by-email email)]
    (password/check password (:password user))))
