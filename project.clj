(defproject tin-whistle-tabs "0.5.0-SNAPSHOT"
  :description "GUI tool that shows a D tin whistle fingering charts."
  :url "https://github.com/gsnewmark/tin-whistle-tabs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"stuart" "http://stuartsierra.com/maven2"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [seesaw "1.4.1"]
                 [com.stuartsierra/lazytest "1.2.3"]
                 [midje "1.4.0"]]
  :main tin-whistle-tabs.core)
