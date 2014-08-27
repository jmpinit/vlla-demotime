(ns demotime.handler
  (:use demotime.views
        clojure.java.io
        [clojure.java.shell :only [sh]])
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.core.async :as async]))

(def top-wrapper "try {")
(def bottom-wrapper "} catch(e) { print(e); }")

(defn js-alive?
  [proc]
  (try (do (.exitValue proc) false)
       (catch IllegalThreadStateException e true)))

(defn exec-js
  [filename]
  (let [cmd (str "/home/owen/projects/vlla-js/bin/vlla-js " filename)]
    (do
      (println (str "executing js from " filename))
      (.exec (Runtime/getRuntime) cmd))))

(def js-chan (async/chan))

(async/go
  (loop [js-proc nil]
    (let [filename (async/<! js-chan)]
      (if (not (nil? js-proc))
        (do
          (if (js-alive? js-proc)
            (do
              (println "destroying old js process")
              (.destroy js-proc)))))
      (recur (exec-js filename)))))
    
(defroutes app-routes
  (GET "/" [] (editor-page))
  (GET "/help" [] (help-page))
  (POST "/demo" req
        (let [params (:params req)
              name (:name params)
              code (:code params)
              filename (str "/tmp/" name ".js")]
          (do
            (with-open [wrtr (writer filename)]
              (.write wrtr (str top-wrapper code bottom-wrapper)))
            (async/>!! js-chan filename)
            "success")))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
