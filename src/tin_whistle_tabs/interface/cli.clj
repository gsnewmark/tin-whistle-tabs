;; ## CLI namespace.
(ns tin-whistle-tabs.interface.cli
  (:use [tin-whistle-tabs.interface.common])
  (:require [tin-whistle-tabs.transformation-api :as api]))


;; ## Declarations

(declare abc->tab)
(declare transform-notes)
(declare generate-help)
(declare replace-unsupported-notes)

;; ## Possible CLI modes
(def modes {:abc {:command "-abc" :help (str "creates a tab for "
                                             "a tune from an ABC file, "
                                             "takes one argument - name "
                                             "of a file with ABC notation")}
            :note {:command "-note" :help (str "creates a charts for a given "
                                               "notes, arguments - any "
                                               "number of notes")}})

;; ## CLI itself

(defn cli
  "CLI interaction with a user. First argument defines mode, all other - arguments to a mode. "
  ([args]
     (cli args api/no-such-note-error))
  ([args default-tab]
     (if-not (nil? args)
       (let [mode (first args) args (rest args)]
         (println
          (str title
               (cond
                (= mode ((modes :abc) :command))
                (abc->tab args default-tab)
                (= mode ((modes :note) :command))
                (transform-notes args default-tab)
                :else (generate-help)))))
       (println blank-input-message))))

;; ## Helpers

(defn abc->tab
  [[file] default-tab]
  "Generates a tab for a tune stored in a ABC-notation file specified as a first argument. Unsupported notes are replaced by a default-tab."
  (let [abc-string (slurp file)]
    (->> (api/abc->tab abc-string)
         (replace-unsupported-notes default-tab)
         (map #(format "%s\n" %))
         (apply str))))

(defn transform-notes
  "Transforms a given list of notes to a string with the corresponding fingerings. Arguments are a list of notes and a default tab (to use with an unsupported note). If default tab is nil, uses a fingering from transformed-notes (empty vector)."
  [args default-tab]
  (->> (api/transform-notes args)
       (replace-unsupported-notes default-tab)
       (interleave args)
       (partition 2)
       (map #(format "%-2s - %s" (first %) (last %)))
       (interpose "\n")
       (apply str)))

(defn generate-help
  "Generates help message for a CLI usage."
  []
  (->> (map #(let [info (second %)]
           (format "%-6s - %s" (info :command) (info :help)))
            modes)
       (interpose "\n")
       (apply str help-title "\n")))

(defn replace-unsupported-notes
  "Replaces the unsupported notes ([]) by a supplied rpl. Doesn't replace if rpl is nil."
  [rpl seq] (if-not (nil? rpl) (map #(if (empty? %) rpl %) seq) seq))
