(ns shape.fields)

(defn- text-renderer [{:keys [identifier name content-id value prefix suffix]}]
  [:label name
   (when prefix
     [:span prefix])
   [:input {:type "text"
            :value value
            :name (str identifier)
            :hx-post (str "/admin/api/content-item/" content-id "/update-field")
            :hx-trigger "keyup changed delay:250ms"}]
   (when suffix
     [:span suffix])])

(defn text [{:keys [identifier name] :as opts}]
  {:identifier identifier
   :name name
   :renderer #(text-renderer (merge opts %))})
