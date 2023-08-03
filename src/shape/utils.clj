(ns shape.utils)

(defn ->merge [& maps]
  (letfn [(reconcile-keys [val-in-result val-in-latter]
            (if (and (map? val-in-result)
                     (map? val-in-latter))
              (merge-with reconcile-keys val-in-result val-in-latter)
              val-in-latter))
          (reconcile-maps [result latter]
            (merge-with reconcile-keys result latter))]
    (reduce reconcile-maps maps)))

(defn request->url [request]
  (let [https? (= (:scheme request) :https)
        host (get-in request [:headers "host"])]
    (if https? (str "https://" host) (str "http://" host))))
