(ns demotime.views 
  (:use [hiccup core page]))

(defn my-include-js
  [& files]
  (let [full-paths (map #(str "/js/" %) files)]
    (apply include-js full-paths)))

(defn my-include-css
  [& files]
  (let [full-paths (map #(str "/css/" %) files)]
    (apply include-css full-paths)))

(defn help-page []
  (html5
    [:head
     [:title "help"]
     (my-include-css "editor.css" "bootstrap.min.css" "bootstrap-theme.min.css")
     (my-include-js "jquery-2.1.1.min.js")]
    [:body
     [:div.container
      [:h1 "help"]
      [:hr]
      [:h2 "api"]
      [:ul
       [:li [:b "paint(x, y): "] "change the color of the pixel at (x, y) to the current palette color."]
       [:li [:b "palette(r, g, b): "] "change the current color."]
       [:li [:b "refresh(): "] "put changes onto the display."]
       [:li [:b "t(): "] "get the current frame count. Useful for making animations."]]]]))

(defn editor-page []
  (html5
    [:head
     [:title "demotime"]
     (my-include-css "editor.css" "bootstrap.min.css" "bootstrap-theme.min.css")
     (my-include-js "jquery-2.1.1.min.js")]
    [:body {:onload "init()"}
     [:canvas {:id "viewport" :width 600 :height 320}] [:br]
     [:button.btn {:id "preview"} "preview"]
     [:input#name {:type "text"}]
     [:button.btn {:id "submit"} "submit"]

     [:br]
     [:hr]
     [:input {:class "color {onImmediateChange:'updateInfo(this);'}" :value "66ff00"}]
     [:p "R:"] [:input#info-r {:size "2"}]
     [:p "G:"] [:input#info-g {:size "2"}]
     [:p "B:"] [:input#info-b {:size "2"}]
     [:hr]

     [:textarea#editor {:cols 120 :rows 240 :data-editor ""} "for(var y=0; y < 32; y++) {
  for(var x=0; x < 60; x++) {
    var c = x ^ y * 4 & 0xFF;
    palette(c, c, c);
    paint(x, y);
  }
}

refresh();"]
     (my-include-js "bootstrap.min.js" "ace/ace.js" "chance.min.js" "color.js" "editor.js")]))

