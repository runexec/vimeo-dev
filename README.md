# Vimeo-dev

Access and work with the Vimeo API from Clojure with Vimeo-dev.

This Clojure library supports both the simple and advanced Vimeo APIs.


# Documentation

0. [Install Vimeo-dev](#installation)
1. [Simple API Examples](#simple-api-examples)
2. [Advanced API Examples](#advanced-api-examples)

# Installation

```bash
git clone https://github.com/runexec/vimeo-dev.git
cd vimeo-dev; lein install
cd ~/; lein new test-project
cp vimeo-dev/api.config.example test-project/api.config
```

Lein Dependency

```clojure
[vimeo-dev/vimeo-dev "0.1.0-SNAPSHOT"]
```

# Simple API Examples 

1. [Init](#init)
2. [User](#user)
3. [User Activity](#user-activity)
4. [Video](#video)
6. [Group](#group)
7. [Channel](#channel)
8. [Album](#album)

#### Init

Load Vimeo Simple API 

```

user> (use 'vimeo-dev.simple)
nil

user> (vimeo-dev.simple/load-simple-api)
nil
```

#### User

```

user> (def user-info
	   (user/is "tylerwsmith" 
		    (user/info)))
#'user/user-info

user> (first user-info)
["total_videos_liked" 374]

user> (def video-page-1 
	   (user/is "tylerwsmith"
		    (user/videos)))
#'user/video-page-1

user> (def video-page-2
	   (user/is "tylerwsmith"
		    (user/videos 2)))
#'user/video-page-2

user> (first video-page-1)
{"embed_privacy" "anywhere", "user_portrait_medium" ... removed for example ...}

user> 

user> (def likes-page-2
	   (user/is "tylerwsmith"
		    (user/likes 2)))
#'user/likes-page-2

user> (def appears-in-page-2
	   (user/is "tylerwsmith"
		    (user/appears-in 2)))
#'user/appears-in-page-2

user> (def all-videos-page-2
      	   (user/is "tylerwsmith"
	   		    (user/all-videos 2)))
#'user/all-videos-page-2

user> (def subscriptions-page-2
	   (user/is "tylerwsmith"
		    (user/subscriptions 2)))
#'user/subscriptions-page-2

user> (def albums-page-2
	   (user/is "tylerwsmith"
		    (user/albums 2)))
#'user/albums-page-2

user> (def channels-page-2
	   (user/is "tylerwsmith"
		    (user/channels 2)))
#'user/channels-page-2

user> (def groups-page-2
	   (user/is "tylerwsmith"
		    (user/groups 2)))
#'user/groups-page-2

user> (user/is "tylerwsmith"
               ;; unlimited arguments for user/is *userid*
               ;; unlimited arguments for user/info-is *userinfo*
	       (user/info-is (user/info)
                             (let [id (user/id)
                                   name (user/display-name)
                                   created-date (user/created-on)
                                   staff? (user/staff?)
                                   plus? (user/plus?)
                                   location (user/location)
                                   url (user/url)
                                   bio (user/bio)
                                   url-profile (user/profile-url)
                                   url-videos (user/videos-url)
                                   count-uploaded (user/total-videos-uploaded)
                                   count-appears-in (user/total-videos-appears-in)
                                   count-liked (user/total-videos-liked)
                                   count-contact (user/total-contact)
                                   count-albums (user/total-albums)
                                   count-channels (user/total-channels)
                                   url-small-photo (user/portrait-small)
                                   url-medium-photo (user/portrait-medium)
                                   url-large-photo (user/portrait-large)]
                               (println staff?))))
1
nil
```

#### User Activity

```

user> (user/is "tylerwsmith"
	       (let [did-page-2 (activity/user-did 2)
				to-user-page-2 (activity/happened-to-user 2)
				contacts-did-page-2 (activity/contacts-did 2)
				everyone-did-page-1 (activity/everyone-did)]
		 (first did-page-2)))
{"action_tags" "", "video_thumbnail_large" ... removed for example ... }
```


#### Video

```

user> (video/is "66222538" (video/title))
"POINTLESS #23"

user> (video/is "66222538" (video/url))
"http://vimeo.com/66222538"

user> (video/is "66222538" (video/id))
66222538

user> (video/is "66222538" (video/description))
""

user> (video/is "66222538" (video/thumbnail-small))
"http://b.vimeocdn.com/ts/437/536/437536915_100.jpg"

user> (video/is "66222538" (video/thumbnail-medium))
"http://b.vimeocdn.com/ts/437/536/437536915_200.jpg"

user> (video/is "66222538" (video/thumbnail-large))
"http://b.vimeocdn.com/ts/437/536/437536915_640.jpg"

user> (video/is "66222538" (video/user-name))
"Brian Redban"

user> (video/is "66222538" (video/user-url))
"http://vimeo.com/redban"

user> (video/is "66222538" (video/upload-date))
"2013-05-15 03:40:29"

user> (video/is "66222538" (video/user-portrait-small))
"http://b.vimeocdn.com/ps/828/828752_30.jpg"

user> (video/is "66222538" (video/user-portrait-medium))
"http://b.vimeocdn.com/ps/828/828752_75.jpg"

user> (video/is "66222538" (video/user-portrait-large))
"http://b.vimeocdn.com/ps/828/828752_100.jpg"

user> (video/is "66222538" 
		{:likes (video/status-number-of-likes)
		 :views (video/status-number-of-views)
		 :comments (video/status-number-of-comments)})
{:likes nil, :views nil, :comments nil}

user> (video/is "66222538" 
		(println "Video is " (video/duration)
			 "\n w x h = " 
			 (video/width) " " (video/height)
			 "\n" (video/tags)))
			 
Video is  5277 
 w x h =  640   360 
 
nil
```

#### Group

```

user> (group/is "shortfilms"
		(let [v-page-1 (group/videos)
		      v-page-2 (group/videos 2)
		      u-page-3 (group/users 3)
		      info (group/info)]
		  (first info)))
		      
       
["total_upcoming_events" 0]

user> (group/is "shortfilms"
               ;; unlimited arguments for group/is *groupid*
               ;; unlimited arguments for group/info-is *groupinfo*
		(group/info-is (group/info)
			       (let [id (group/id)
				     name (group/name)
				     desc (group/description)
				     url (group/url)
				     logo (group/logo)
				     thumbnail (group/thumbnail)
				     created-on (group/created-on)
				     creator-id (group/creator-id)
				     creator-name (group/creator-display-name)
				     creator-url (group/creator-url)
				     total-members (group/total-members)
				     total-videos (group/total-videos)
				     total-files (group/total-files)
				     total-forums-topics (group/total-forum-topics)
				     total-events (group/total-events)
				     total-upcoming-events (group/total-upcoming-events)]
				 (println desc))))
Unleash your creativity.Produce. Transform. Post. Discuss.
nil
```

#### Channel 

```

user> (channel/is "146809"
		  (let [videos-page-1 (channel/videos)
			videos-page-2 (channel/videos 2)
			info (channel/info)]
		    (first info)))

["badge" ""]

user> (channel/is "146809"
		  (channel/info-is (channel/info)
				   (let [id (channel/id)
					 name (channel/name)
					 desc (channel/description)
					 logo (channel/logo)
					 url (channel/url)
					 rss (channel/rss)
					 created-on (channel/created-on)
					 creator-id (channel/creator-id)
					 creator-name (channel/creator-display-name)
					 total-videos (channel/total-videos)
					 total-subscribers (channel/total-subscribers)]
				     (println total-subscribers))))

1136
nil
```

#### Album

```

user> (album/is "2292509"
		(let [page-3 (album/videos 3)
		      info (album/info)]
		  (album/info-is info
				 (let [id (album/id)
				       created-on (album/created-on)
				       last-modified (album/last-modified)
				       title (album/title)
				       desc (album/description)
				       url (album/url)
				       thumbnail (album/thumbnail)
				       total-videos (album/total-videos)
				       user-id (album/user-id)
				       user-name (album/user-display-name)
				       user-url (album/user-url)]
				   (println title)))))
		      
Byzantine Institute Films
nil
```

# Advanced API Examples 

Advanced API calls are easier than you might think. Using the request form, the first argument is the name of the Vimeo Advanced API Method, and the second is the params.
<p>Let's make a call for vimeo.categories.getAll => https://developer.vimeo.com/apis/advanced/methods/vimeo.categories.getAll</p>

```

user> (use 'vimeo-dev.advanced)
nil

user> (request :vimeo.categories.getAll 
	       {:page 1 :per_page 3})
		      
{"generated_in" "0.0178", "stat" "ok", "categories" {"on_this_page" "3", "page" "1", "perpage" "3", "total" "18", "category" ... removed for example ... }
```


## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
