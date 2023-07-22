(ns shape.handlers.rate-limit)

(defn handler [_]
  {:status 429
   :headers {"Content-Type" "text/html"}
   :body "Slow down, you're going too fast."})
