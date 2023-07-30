(ns shape.fields)

(defn- text-renderer [{:keys [identifier label content-id value]}]
  [:label label
   [:input {:type "text"
            :value value
            :name (str identifier)}]])

(defn text [{:keys [identifier label] :as opts}]
  (fn [data]
    {:identifier identifier
     :label label
     :renderer #(text-renderer (merge opts data))}))
