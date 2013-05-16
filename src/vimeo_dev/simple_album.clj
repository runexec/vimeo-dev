(ns vimeo-dev.simple-album
  (:use [cheshire.core 
         :only [parse-stream]]))

;; /api/v2/album/albumid/request.json
(def base-url "http://vimeo.com/api/v2/album/%s/%s.json")

(def ^:dynamic *album*)
(def ^:dynamic *album-info*)

(defmacro is [album-id & body]
  `(binding [*album* ~album-id]
     ~@body))

(defmacro info-is [album-info & body]
  `(binding [*album-info* ~album-info]
     ~@body))

(defn -url [request & [page]]
  (let [request (.. request
                    (replace "-" "_"))
        url (format base-url
                    *album*
                    request)]
    (if-not page
      url
      (str url "?page=" page))))

(defn get-url [request & [page]]
  (let [url (if-not page
              -url
              #(-url % page))]
    (-> (name request)
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
  (get *album-info*
       (-> attribute
           name
           (.replace "-" "_"))))

(defn id []
  (need :id))

(defn created-on []
  (need :created-on))

(defn last-modified []
  (need :last-modified))

(defn title []
  (need :title))

(defn description []
  (need :description))

(defn url []
  (need :url))

(defn thumbnail []
  (need :thumbnail))

(defn total-videos []
  (need :total-videos))

(defn user-id []
  (need :user-id))

(defn user-display-name []
  (need :user-display-name))

(defn user-url []
  (need :user-url))
