(defproject demotime "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [hiccup "1.0.5"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler demotime.handler/app :port 80}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
