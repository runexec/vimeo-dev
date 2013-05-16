(ns vimeo-dev.simple)

(defn load-simple-api []
  (require
   '[vimeo-dev.simple-user 
     :as user]
   '[vimeo-dev.simple-video
     :as video]
   '[vimeo-dev.simple-activity
     :as activity]
   '[vimeo-dev.simple-group
     :as group]
   '[vimeo-dev.simple-channel
     :as channel]
   '[vimeo-dev.simple-album
     :as album]))
