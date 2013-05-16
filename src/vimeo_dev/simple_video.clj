(ns vimeo-dev.simple-video
  (:use [cheshire.core 
         :only [parse-stream]]))

(def ^:dynamic *video*)

(defmacro is [video-id & body]
  (let [url (str "http://vimeo.com/api/v2/video/"
                 video-id
                 ".json")
        data (-> url
                 clojure.java.io/reader
                 parse-stream
                 first)]
    `(binding [*video* ~data]
       ~@body)))

(defn need [attribute]
  (get *video*
       (-> attribute
           name
           (.replace "-" "_"))))
      
(defn title [] (need :title))

(defn url [] (need :url))

(defn id [] (need :id))

(defn description []
  (need :description))

(defn thumbnail-small []
  (need :thumbnail-small))

(defn thumbnail-medium []
  (need :thumbnail-medium))

(defn thumbnail-large []
  (need :thumbnail-large))

(defn user-name []
  (need :user-name))

(defn user-url []
  (need :user-url))

(defn upload-date []
  (need :upload-date))

(defn user-portrait-small []
  (need :user-portrait-small))

(defn user-portrait-medium []
  (need :user-portrait-medium))

(defn user-portrait-large []
  (need :user-portrait-large))

(defn status-number-of-likes []
  (need :status-number-of-likes))

(defn status-number-of-views []
  (need :status-number-of-views))

(defn status-number-of-comments []
  (need :status-number-of-comments))

(defn duration [] (need :duration))

(defn width [] (need :width))

(defn height [] (need :height))

(defn tags [] (need :tags))
