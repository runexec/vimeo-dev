(ns vimeo-dev.simple-channel
  (:refer-clojure :exclude [name])
  (:use [cheshire.core 
         :only [parse-stream]]))

;; /api/v2/channel/channelname/request.json
(def base-url "http://vimeo.com/api/v2/channel/%s/%s.json")

(def ^:dynamic *channel*)
(def ^:dynamic *channel-info*)

(defmacro is [channel-name & body]
  `(binding [*channel* ~channel-name]
     ~@body))

(defmacro info-is [channel-info & body]
  `(binding [*channel-info* ~channel-info]
     ~@body))

(defn -url [request & [page]]
  (let [request (.. request
                    (replace "-" "_"))
        url (format base-url
                    *channel*
                    request)]
    (if-not page
      url
      (str url "?page=" page))))

(defn get-url [request & [page]]
  (let [url (if-not page
              -url
              #(-url % page))]
    (-> (clojure.core/name request)
        url
        clojure.java.io/reader
        parse-stream)))

(defn videos [& [page]]
  (let [_ (partial get-url :videos)]
    (if-not page (_) (_ page))))

(defn info [& [page]]
  (let [_ (partial get-url :info)]
    (if-not page (_) (_ page))))

(defn need [attribute]
  (get *channel-info*
       (-> attribute
           clojure.core/name
           (.replace "-" "_"))))

(defn id []
  (need :id))

(defn name []
  (need :name))

(defn description []
  (need :description))

(defn logo []
  (need :logo))

(defn url []
  (need :url))

(defn rss []
  (need :rss))

(defn created-on []
  (need :created-on))

(defn creator-id []
  (need :creator-id))

(defn creator-display-name []
  (need :creator-display-name))

(defn creator-url []
  (need :creator-url))

(defn total-videos []
  (need :total-videos))

(defn total-subscribers []
  (need :total-subscribers))
