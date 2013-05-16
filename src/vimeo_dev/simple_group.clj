(ns vimeo-dev.simple-group
  (:use [cheshire.core 
         :only [parse-stream]]))

;; /api/v2/group/groupname/request.json
(def base-url "http://vimeo.com/api/v2/group/%s/%s.json")

(def ^:dynamic *group*)
(def ^:dynamic *group-info*)

(defmacro is [group-name & body]
  `(binding [*group* ~group-name]
     ~@body))

(defmacro info-is [group-info & body]
  `(binding [*group-info* ~group-info]
     ~@body))

(defn -url [request & [page]]
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
              -url
              #(-url % page))]
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

(defn need [attribute]
  (get *group-info*
       (-> attribute
           name
           (.replace "-" "_"))))

(defn id []
  (need :id))

(defn name []
  (need :name))

(defn description []
  (need :description))

(defn url []
  (need :url))

(defn logo []
  (need :logo))

(defn thumbnail []
  (need :thumbnail))

(defn created-on []
  (need :created-on))

(defn creator-id []
  (need :creator-id))

(defn creator-display-name []
  (need :creator-display-name))

(defn creator-url []
  (need :creator-url))

(defn total-members []
  (need :total-members))

(defn total-videos []
  (need :total-videos))

(defn total-files []
  (need :total-files))

(defn total-forum-topics []
  (need :total-forum-topics))

(defn total-events []
  (need :total-events))

(defn total-upcoming-events []
  (need :total-upcoming-events))
