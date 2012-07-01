;; ## Interfaces namespace.
;; Functions that provide means of interaction with the user.
(ns tin-whistle-tabs.interface)


;; ## UI constants

;; Title of an app.
(def title "Tin Whistle (D) fingering chart\n")

;; ## CLI

(declare get-cli-answer)

(defn cli
  "Prints notes and their tabs to command-line interface. Arguments are a list of non-transformed notes, a list of transformed notes and a default tab (to use with an unsupported note). Default value if default-tab is nil."
  ([notes transformed-notes]
     (cli notes transformed-notes nil))
  ([notes transformed-notes default-tab]
     (println
      (str title (get-cli-answer notes transformed-notes default-tab)))))

(defn get-cli-answer
  "Transforms a given list of notes to a string with the corresponding fingerings. Arguments are a list of non-transformed notes, a list of transformed notes and a default tab (to use with an unsupported note). If default tab is nil, uses a fingering from transformed-notes (empty vector)."
  [notes transformed-notes default-tab]
  (->> transformed-notes
       (map #(if (empty? %) (if default-tab default-tab %) %))
       (interleave notes)
       (partition 2)
       (map #(format "%-2s - %s" (first %) (last %)))
       (interpose "\n")
       (apply str)))