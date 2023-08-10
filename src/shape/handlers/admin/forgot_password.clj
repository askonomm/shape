(ns shape.handlers.admin.forgot-password
  (:require
   [clojure.string :as string]
   [postal.core :refer [send-message]]
   [dotenv :refer [env]]
   [shape.data :as data]
   [shape.handlers.utils :refer [->admin-page smtp-config]]))

(defn- content [request]
  [:div.wall-content
   [:div.logo]
   [:h2 "Forgot Password?"]
   [:p "We should get that fixed"]
   [:form {:method "post"}
    (when-let [error (:error request)]
      [:div.error error])
    (cond
      (true? (-> request :message-sent?))
      [:p "Check your e-mail for a link to change your password."]

      (false? (-> request :message-sent?))
      [:p "Something went wrong sending the e-mail. Please contact the web administrator."]

      :else
      (list
        [:label
          "E-mail"
          [:input {:type "email"
                   :name "email"
                   :placeholder "you@somewhere.com"
                   :value (-> request :remembered :email)}]]
        [:button {:type "submit"
                  :class "button primary"} "Change password"]))]])

(defn view-handler [request]
  (->admin-page
   (content request)
   {:css ["shape/css/wall"]
    :body-class "wall"}))

(defn send-email [email token url]
  (let [from (env "MAIL_FROM")]
    (send-message (smtp-config)
                  {:X-PM-Message-Stream "outbound"
                   :to email
                   :from from
                   :subject "Reset password"
                   :body (str "Reset your password via this URL: "
                              url "/admin/reset-password/" token)})))

(defn action-handler [request]
  (let [email (get-in request [:form-params "email"])
        url (get-in request [:headers "origin"])]
    (cond
      (string/blank? email)
      (view-handler (assoc request :error "E-mail is required."))

      (not (data/user-exists-by-email? email))
      (view-handler (assoc request :error "No such account found."))

      :else
      (let [user (data/user-by-email email)
            reset-token (str (random-uuid))]
        (data/set-user-reset-token! (:id user) reset-token)
        (if (= (:error (send-email (:email user) reset-token url)) :SUCCESS)
          (view-handler (assoc request :message-sent? true))
          (view-handler (assoc request :message-sent? false)))))))
