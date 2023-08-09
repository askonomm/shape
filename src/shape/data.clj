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
   (-> (datasource)
       (jdbc/execute-one! data {:return-keys return-keys?
                                :builder-fn rs/as-unqualified-kebab-maps}))))

(defn- query!
  ([data]
   (query! data false))
  ([data return-keys?]
   (-> (datasource)
       (jdbc/execute! data {:return-keys return-keys?
                            :builder-fn rs/as-unqualified-kebab-maps}))))

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

(defn content-items [identifier]
  (let [sql "SELECT * FROM content WHERE shape_identifier = ?"
        items (query! [sql identifier])
        sql-fields "SELECT * FROM content_fields WHERE content_id = ?"]
    (->> items
         (map (fn [item]
                (assoc item :fields (query! [sql-fields (:id item)])))))))

(defn content-item-exists? [id]
  (let [sql "SELECT * FROM content WHERE id = ?"]
    (boolean (query-one! [sql id]))))

(defn content-item-field [content-id identifier]
  (let [sql "SELECT * FROM content_fields WHERE content_id = ? AND identifier = ?"]
    (query-one! [sql content-id identifier])))

(defn create-content-item!
  [{:keys [shape-identifier]}]
  (let [sql "INSERT INTO content (shape_identifier, created_at) VALUES (?, ?)"
        created-at (System/currentTimeMillis)
        result (query-one! [sql shape-identifier created-at] true)
        kw (keyword "last-insert-rowid()")]
    (kw result)))

(defn set-content-item-field!
  [content-id identifier value]
  (if (query-one! ["SELECT * FROM content_fields WHERE content_id = ? AND identifier = ?" content-id identifier])
    (let [sql "UPDATE content_fields SET value = ? WHERE content_id = ? AND identifier = ?"]
      (query-one! [sql value content-id identifier]))
    (let [sql "INSERT INTO content_fields (content_id, identifier, value) VALUES (?, ?, ?)"]
      (query-one! [sql content-id identifier value]))))

(defn delete-content-item!
  [content-id]
  (let [sql "DELETE FROM content WHERE id = ?"]
    (query-one! [sql content-id])))