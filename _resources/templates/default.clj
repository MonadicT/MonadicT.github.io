;;(doctype :xhtml-transitional)
[:html
 {:xmlns "http://www.w3.org/1999/xhtml", :lang "en", :xml:lang "en"}
 [:head
  [:meta
   {:http-equiv "content-type", :content "text/html; charset=UTF-8"}]
  [:meta {:name "description", :content (:description metadata)}]
  [:meta {:name "keywords", :content (:tags metadata)}]
  [:meta {:name "author", :content "Praki Prakash"}]
  [:link {:rel "icon",
	  :href "/images/favicon.ico" :type "image/x-icon"}]
  [:link {:rel "shortcut icon",
	  :href "/images/favicon.ico" :type "image/x-icon"}]
  [:link {:rel "stylesheet", :type "text/css", :href "/default.css"}]
  [:link
   {:rel "alternate", :type "application/rss+xml",
    :title (:site-title (static.config/config)), :href "/rss-feed"}]

  (if (= (:type metadata) :post)
    [:link {:rel "canonical"
	    :href (str (:site-url (static.config/config)) (:url metadata))}])

  [:script {:src "http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML",
            :type "text/javascript"}]
  [:title (:title metadata)]]

 [:div#wrap
 [:body
  [:div#header
   [:form {:method "get"
           :action "http://www.google.com/search" :id "searchform"}
    [:div
     [:input {:type "text" :name "q" :class "box" :id "s"}]
     [:input {:type "hidden" :name "sitesearch"
              :value (:site-url (static.config/config))}]]]
    [:h1
     [:a
      {:href "/"}
      "MonadicT"]]
    #_[:div
     {:class "pages"}
     [:a {:href "/", :class "page"} "Home"] " | "
     [:a {:href "/projects.html", :class "page"} "Projects"] " | "
     [:a {:href "/archives.html", :class "page"} "Archives"] " | "
     [:a {:href "/tags/", :class "page"} "Tags"] " | "
     [:a {:href "/contact.html", :class "page" :rel "author"} "About"]

     [:form {:method "get"
	     :action "http://www.google.com/search" :id "searchform"}
      [:div
       [:input {:type "text" :name "q" :class "box" :id "s"}]
       [:input {:type "hidden" :name "sitesearch"
		:value (:site-url (static.config/config))}]]]]]
  [:div#content-wrap
   [:div#content
    [:div
     {:id "post"}
     (if (or (= (:type metadata) :post)
	     (= (:type metadata) :site))
       [:h2 {:class "page-title"} (:title metadata)])

     content

     (if (= (:type metadata) :post)
       (reduce
     	(fn[h v]
     	  (conj h [:a {:href (str "/tags/#" v)} (str v " ")]))
     	[:div {:class "post-tags"} "Tags: "]
     	(.split (:tags metadata) " ")))]

    (if (= (:type metadata) :post)
      [:div
       {:id "related"}
       [:h3 {:class "random-posts"} "Random Posts"]
       [:ul
    	{:class "posts"}
	(map
	 #(let [f %
		url (static.core/post-url f)
		[metadata _] (static.io/read-doc f)
		date (static.core/parse-date
		      "yyyy-MM-dd" "dd MMM yyyy"
		      (re-find #"\d*-\d*-\d*" (str f)))]
	   [:li [:span date] [:a {:href url} (:title metadata)]])
	 (take 5 (shuffle (static.io/list-files :posts))))]])

    #_[:div {:id "disqus"}
     (if (= (:type metadata) :post)
       "<div id=\"disqus_thread\"></div><script type=\"text/javascript\" src=\"http://disqus.com/forums/monadict/embed.js\"></script><noscript><a href=\"http://disqus.com/forums/mondaict/?url=ref\">View the discussion thread.</a></noscript><a href=\"http://disqus.com\" class=\"dsq-brlink\">blog comments powered by <span class=\"logo-disqus\">Disqus</span></a>")]]
   [:div#sidebar
    [:div {:style "border-bottom: solid 1px #ddd"}
     "PRAKI PRAKASH"]
    [:div
     [:img {:src "/images/praki-outline.png" :style "float:left;padding:
                                                .5em"}]
     "I'm just an ordinary bloke! I like building things, real as well
     as imaginary. Check my instagram link below for my non-technical
     side."
     [:span {:style "visibility: hidden"}
     "Maker, programmer, culinarian, but not necessarily in
      that order! Toiled at VMware, Yahoo, Westbridge and
      Arzoo. Recovering Java programmer, functional
      programming ideologue and Clojure practitioner. Maker of
      very thin wood shavings using antique hand
      tools. Inventor of delectable culinary chef d'œuvre!"]
     ]
    [:div {:style "padding-top: 1em; padding-bottom:1em"}
     [:a {:href "//github.com/MonadicT"
          :target"_top" :style "text-decoration:none;"}
      [:img {:src "/images/GitHub-Mark-32px.png"
             :alt "Github" :title "GitHub"
             :style"border:0;width:16px;height:16px;"}]]
     "&nbsp;&nbsp;"
     [:a {:href "//plus.google.com/106671634457466903954?prsrc=3"
          :rel "publisher" :target "_top" :style "text-decoration:none;"}
      [:img {:src "//ssl.gstatic.com/images/icons/gplus-32.png"
             :alt "Google+" :title "Google+"
              :style "border:0;width:16px;height:16px;"}]]
     "&nbsp;&nbsp;"
     [:a {:href "https://twitter.com/MonadicT"}
      [:img {:src "/images/Twitter_logo_blue.png"
             :alt "Twitter" :title "Twitter"
             :style "border:0;width:16px;height:16px"}]]
     [:a {:href "https://www.instagram.com/fgx3prak/?ref=badge" :class "ig-b- ig-b-24"}
      [:img {:src "//badges.instagram.com/static/images/ig-badge-16.png"
             :style "padding-left:4px;width:16px;height:16px"
             :height "16px" :width "16px" :alt "Instagram"}]]
     [:a#rss-feed {:href "/rss-feed"} [:img {:src "/images/rss.png"
                                             :height "16px"
                                             :width "16px"}]]]
    [:a.twitter-timeline {:href "https://twitter.com/MonadicT/favorites"
                          :data-widget-id "438551614102577152"}
     "Favorite Tweets by @MonadicT"]
    [:script {:type "text/javascript"} "function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document,'script','twitter-wjs');"]]]

  [:div#footer
   [:p "&copy; 2013-2015"
    [:a {:href (:site-url (static.config/config))} " Praki Prakash"]]]
  ;;
  ;;
  #_(if (= (:type metadata) :post)
    "<script type=\"text/javascript\">
//<![CDATA[
(function() {
	     var links = document.getElementsByTagName('a');
	     var query = '?';
	     for(var i = 0; i < links.length; i++) {
		     if(links[i].href.indexOf('#disqus_thread') >= 0) {
								       query += 'url' + i + '=' + encodeURIComponent(links[i].href) + '&';
								       }
		     }
	     document.write('<script charset=\"utf-8\" type=\"text/javascript\" src=\"http://disqus.com/forums/monadict/get_num_replies.js' + query + '\"></' + 'script>');
	     })();
//]]>
</script>")]]]
