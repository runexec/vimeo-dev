(ns vimeo-dev.advanced
  (:use [cheshire.core 
         :only [parse-string]])
  (:import [org.scribe.builder ServiceBuilder]
           [org.scribe.builder.api VimeoApi]
           [org.scribe.model
            Token
            Verifier
            Verb
            OAuthRequest]))

(defn oauth-settings []
  (let [config (-> "api.config"
                   slurp
                   clojure.string/split-lines)
        settings (transient {})]
    (doseq [_ config
            :let [[k v] (-> _
                            (clojure.string/split #"\s"))
                  k (keyword k)]]
      (assoc! settings k v))
    (persistent! settings)))

(let [oa-_ (oauth-settings)
      oa-key (:consumer-key oa-_)
      oa-secret (:consumer-secret oa-_)
      oa-token (:access-token oa-_)
      oa-token-secret (:access-token-secret oa-_)
      oa-auth (Token. oa-token oa-token-secret)]

  (def oauth (.build
              (doto (ServiceBuilder.)
                (.apiKey oa-key)
                (.apiSecret oa-secret)
                (.provider VimeoApi))))

  (defn request [method params-map]
    (let [url (str "http://vimeo.com/api/rest/v2?format=json&method="
                   (if (keyword method) (name method) (str method))
                   (apply str
                          (map (fn [k v]
                                 (let [_ #(java.net.URLEncoder/encode %)
                                       __ #(if (keyword? %) (name %) (str %))
                                       k (__ k)
                                       v (__ v)]
                                   (str "&" k "=" v)))
                               (keys params-map)
                               (vals params-map))))
          req (OAuthRequest. Verb/POST url)]
      (.. oauth 
          (signRequest oa-auth req))
      (let [sent (.send req)
            code (.getCode sent)
            body (.getBody sent)]
        (if (or (< code 200)
                (>= code 300))
          (-> (str code " :: " body)
              Exception.
              throw))
        (parse-string body)))))
