(ns demotime.handler
  (:use demotime.views
        clojure.java.io
        [clojure.java.shell :only [sh]])
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(def top-wrapper "try {")
(def bottom-wrapper "} catch(e) { print(e); }")

(defroutes app-routes
  (GET "/" [] (editor-page))
  (POST "/demo" req
        (let [params (:params req)
              name (:name params)
              code (:code params)
              filename (str "/tmp/" name ".js")]
          (do
            (with-open [wrtr (writer filename)]
              (.write wrtr (str top-wrapper code bottom-wrapper)))
            (println (str "wrote to " filename))
            (:out (sh "/home/owen/projects/vlla-js/bin/vlla-js" filename)))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
