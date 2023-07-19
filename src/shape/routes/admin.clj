(ns shape.routes.admin)

(def ^:private dashboard
  {:get {:responses {200 {:body string?}}
         :handler (fn [_] 
                    {:status 200
                     :body "Hello from admin"})}})

(def ^:private login
  {:get {:responses {200 {:body string?}}
         :handler (fn [_]
                    {:status 200
                     :body "Login"})}})

(def routes
  [["" dashboard]
   ["/login" login]])
