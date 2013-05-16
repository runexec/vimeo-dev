(ns vimeo-dev.simple-activity
  (:use [cheshire.core 
         :only [parse-stream]]
        [vimeo-dev.simple-user
         :only [*user*]]))

;; /api/v2/activity/username/request.json
(def base-url "http://vimeo.com/api/v2/activity/%s/%s.json")

(defn url [request & [page]]
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
              url
              #(url % page))]
    (-> (name request)
        url
        clojure.java.io/reader
        parse-stream)))

(defn user-did [& [page]]
  (let [_ (partial get-url :user-did)]
    (if-not page (_) (_ page))))

(defn happened-to-user [& [page]]
  (let [_ (partial get-url :happened-to-user)]
    (if-not page (_) (_ page))))

(defn contacts-did [& [page]]
  (let [_ (partial get-url :contacts-did)]
    (if-not page (_) (_ page))))

(defn everyone-did [& [page]]
  (let [_ (partial get-url :everyone-did)]
    (if-not page (_) (_ page))))

