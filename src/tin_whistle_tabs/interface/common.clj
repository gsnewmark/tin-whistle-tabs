;; ## Common interface elements namespace
(ns tin-whistle-tabs.interface.common)


;; ## UI constants

;; Title of an app.
(def title "Tin Whistle (D)\n")

;; Text for blank input.
(def blank-input-message "Blank input.")

;; Text for unsupported input.
(def unsupported-input-message "Unsupported arguments.")

;; Title of help section.
(def help-title "Help")

;; ## Helpers

(defn replace-unsupported-notes
  "Replaces the unsupported notes ([]) by a supplied rpl. Doesn't replace if rpl is nil."
  [rpl seq] (if-not (nil? rpl) (map #(if (empty? %) rpl %) seq) seq))