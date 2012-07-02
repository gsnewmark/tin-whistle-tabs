;; ## Interfaces namespace.
;; Functions that provide means of interaction with the user.
(ns tin-whistle-tabs.interface
  (:require [tin-whistle-tabs.transformation-api :as api]))


;; ## UI constants

;; Title of an app.
(def title "Tin Whistle (D) fingering chart\n")

;; Text for blank input.
(def blank-input-message "Blank input.")

;; ## CLI

(declare get-cli-answer)

(defn cli
  "Prints notes and their tabs to command-line interface. Arguments are a list of non-transformed notes, a list of transformed notes and a default tab (to use with an unsupported note)."
  ([args]
     (cli args api/no-such-note-error))
  ([args default-tab]
     (if-not (nil? args)
      (println
       (str title (get-cli-answer args default-tab)))
      (println blank-input-message))))

(defn get-cli-answer
  "Transforms a given list of notes to a string with the corresponding fingerings. Arguments are a list of non-transformed notes, a list of transformed notes and a default tab (to use with an unsupported note). If default tab is nil, uses a fingering from transformed-notes (empty vector)."
  [args default-tab]
  (->> (api/transform-notes args)
       (map #(if (empty? %) (if default-tab default-tab %) %))
       (interleave args)
       (partition 2)
       (map #(format "%-2s - %s" (first %) (last %)))
       (interpose "\n")
       (apply str)))