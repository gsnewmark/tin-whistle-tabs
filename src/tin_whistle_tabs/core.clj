;; ## Main tool's namespace
;; Functions that provides an interface for a user and their invocation.
(ns tin-whistle-tabs.core
  (:require [tin-whistle-tabs.transformation-api :as api]))


;; ## Interface declarations

(declare cli-interface)

;; ## Program's entry point

(defn -main
  "Starts a user interface of a tool."
  [& args]
  (println "Tin Whistle (D) fingering chart")
  (cli-interface args))

;; ## Interface implementation

(defn cli-interface
  "Transforms a given list of notes to a corresponding fingerings and prints them."
  [notes]
  (println
   (apply str
          (interpose "\n"
                     (map
                      #(format "%-2s - %s" (first %) (last %))
                      (partition 2
                                 (interleave notes
                                             (api/transform-notes notes))))))))
