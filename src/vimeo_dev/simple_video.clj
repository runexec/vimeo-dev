(ns vimeo-dev.simple-video
  (:use [cheshire.core 
         :only [parse-stream]]))

(defn data [video-id & [page]]
  (let [url (str "http://vimeo.com/api/v2/video/"
                 video-id
                 ".json"
                 (when page
                   (str "?page=" page)))]
    (-> url
        clojure.java.io/reader
        parse-stream)))
