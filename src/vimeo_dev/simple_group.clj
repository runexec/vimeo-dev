(ns vimeo-dev.simple-group
  (:use [cheshire.core 
         :only [parse-stream]]))

;; /api/v2/group/groupname/request.json
(def base-url "http://vimeo.com/api/v2/group/%s/%s.json")

(def ^:dynamic *group*)

(defmacro is [group-name & body]
  `(binding [*group* ~group-name]
     ~@body))

(defn url [request & [page]]
  (let [request (.. request
                    (replace "-" "_"))
        url (format base-url
                    *group*
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

(defn users [& [page]]
  (let [_ (partial get-url :users)]
    (if-not page (_) (_ page))))

(defn info [& [page]]
  (let [_ (partial get-url :info)]
    (if-not page (_) (_ page))))
