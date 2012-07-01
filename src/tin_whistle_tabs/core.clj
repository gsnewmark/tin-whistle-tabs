;; ## Main tool's namespace
;; Functions that provides an interface for a user and their invocation.
(ns tin-whistle-tabs.core
  (:require [tin-whistle-tabs.transformation-api :as api]))


;; ## Interface declarations

(declare title)
(declare cli-interface)

;; ## Program's entry point

(defn -main
  "Starts a user interface of a tool."
  [& args]
  (println (str title (cli-interface args))))

;; ## Interface implementation

;; Title of an app.
(def title "Tin Whistle (D) fingering chart\n")

(defn cli-interface
  "Transforms a given list of notes to a string with the corresponding fingerings."
  [notes]
  (->> (api/transform-notes notes)
       (map #(if (empty? %) api/no-such-note-error %))
       (interleave notes)
       (partition 2)
       (map #(format "%-2s - %s" (first %) (last %)))
       (interpose "\n")
       (apply str)))


