(ns shape.data
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]
    [dotenv :refer [env]]
    [crypto.password.bcrypt :as pw]))

(defn db-url []
  (if-not (nil? (env "DB_URL"))
    (env "DB_URL")
    "jdbc:sqlite:shape.db"))

(defn datasource []
  (jdbc/get-datasource (db-url)))

(defn- query-one!
  ([data]
   (query-one! data false))
  ([data return-keys?]
   (jdbc/execute-one! (datasource) data {:return-keys return-keys?
                                         :builder-fn rs/as-unqualified-kebab-maps})))

(defn user-by-token
  [token]
  (let [sql "SELECT * FROM users WHERE token = ?"]
    (query-one! [sql token])))

(defn user-by-reset-token
  [token]
  (let [sql "SELECT * FROM users WHERE reset_token = ?"]
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
        created-at (System/currentTimeMillis)
        result (query-one! [sql email (pw/encrypt password) role created-at] true)
        kw (keyword "last-insert-rowid()")]
    (kw result)))

(defn set-user-token!
  [user-id token]
  (let [sql "UPDATE users SET token = ? WHERE id = ?"]
    (query-one! [sql token user-id])))

(defn set-user-reset-token!
  [user-id token]
  (let [sql "UPDATE users SET reset_token = ? WHERE id = ?"]
    (query-one! [sql token user-id])))

(defn set-user-password!
  [user-id password]
  (let [sql "UPDATE users SET password = ? WHERE id = ?"]
    (query-one! [sql (pw/encrypt password) user-id])))

(defn user-exists-by-email?
  [email]
  (boolean (user-by-email email)))

(defn user-authenticates?
  [email password]
  (when-let [user (user-by-email email)]
    (pw/check password (:password user))))

(defn content-item-exists? [id]
  (let [sql "SELECT * FROM content WHERE id = ?"]
    (boolean (query-one! [sql id]))))

(defn content-item-field [content-id field-identifier]
  (let [sql "SELECT * FROM content_fields WHERE content_id = ? AND field_identifier = ?"]
    (query-one! [sql content-id field-identifier])))

(defn create-content-item!
  [{:keys [shape-identifier]}]
  (let [sql "INSERT INTO content (shape_identifier, created_at) VALUES (?, ?)"
        created-at (System/currentTimeMillis)
        result (query-one! [sql shape-identifier created-at] true)
        kw (keyword "last-insert-rowid()")]
    (kw result)))
