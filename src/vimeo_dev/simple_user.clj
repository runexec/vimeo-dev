(ns vimeo-dev.simple-user
  (:use [cheshire.core 
         :only [parse-stream]]))

;; /v2/username/request.json
(def base-url "http://vimeo.com/api/v2/%s/%s.json")

(def ^:dynamic *user*)
(def ^:dynamic *user-info*)

(defmacro is [username & body]
  `(binding [*user* ~username]
     ~@body))

(defmacro info-is [user-info & body]
  `(binding [*user-info* ~user-info]
     ~@body))

(defn -url [request & [page]]
  (let [request (.. request
                    (replace "-" "_"))
        url (format base-url
                    *user*
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

(defn info [] (get-url :info))

(defn videos [& [page]]
  (let [_ (partial get-url :videos)]
    (if-not page (_) (_ page))))

(defn likes [& [page]]
  (let [_ (partial get-url :likes)]
    (if-not page (_) (_ page))))

(defn appears-in [& [page]]
  (let [_ (partial get-url :appears-in)]
    (if-not page (_) (_ page))))

(defn all-videos [& [page]]
  (let [_ (partial get-url :all-videos)]
    (if-not page (_) (_ page))))

(defn subscriptions [& [page]]
  (let [_ (partial get-url :subscriptions)]
    (if-not page (_) (_ page))))

(defn albums [& [page]]
  (let [_ (partial get-url :albums)]
    (if-not page (_) (_ page))))

(defn channels [& [page]]
  (let [_ (partial get-url :channels)]
    (if-not page (_) (_ page))))

(defn groups [& [page]]
  (let [_ (partial get-url :groups)]
    (if-not page (_) (_ page))))

(defn need [attribute]
  (get *user-info*
       (-> attribute
           name
           (.replace "-" "_"))))


(defn id [] 
  (need :id))

(defn display-name []
  (need :display-name))

(defn created-on []
  (need :created-on))

(defn staff? []
  (need :is-staff))

(defn plus? []
  (need :is-plus))

(defn location []
  (need :location))

(defn url []
  (need :url))

(defn bio []
  (need :bio))

(defn profile-url []
  (need :profile-url))

(defn videos-url []
  (need :videos-url))

(defn total-videos-uploaded []
  (need :total-videos-uploaded))

(defn total-videos-appears-in []
  (need :total-videos-appears-in))

(defn total-videos-liked []
  (need :total-videos-liked))

(defn total-contact []
  (need :total-contact))

(defn total-albums []
  (need :total-albums))

(defn total-channels []
  (need :total-channels))

(defn portrait-small []
  (need :portrait-small))

(defn portrait-medium []
  (need :portrait-medium))

(defn portrait-large []
  (need :portrait-large))
