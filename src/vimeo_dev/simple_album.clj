(ns vimeo-dev.simple-album
  (:use [cheshire.core 
         :only [parse-stream]]))

;; /api/v2/album/albumid/request.json
(def base-url "http://vimeo.com/api/v2/album/%s/%s.json")

(def ^:dynamic *album*)

(defmacro is [album-id & body]
  `(binding [*album* ~album-id]
     ~@body))

(defn url [request & [page]]
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
              url
              #(url % page))]
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

