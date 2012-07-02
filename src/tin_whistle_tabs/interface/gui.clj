;; ## GUI namespace.
(ns tin-whistle-tabs.interface.gui
  (:use [tin-whistle-tabs.interface.common]
        [seesaw.core])
  (:require [tin-whistle-tabs.transformation-api :as api]))


;; ## Declarations
(declare show-main-frame)
(declare activate-listeners)
(declare display)

;; ## GUI elements

;; App's main window.
(def main-frame (frame :title title
                       :width 200
                       :height 200
                       :resizable? false))

;; Submit button.
(def submit (button :text "Ok"))

;; Notes listbox.
;; TODO show notes in a correct 'whistle' order
(def notes-listbox
  (listbox :model (->> api/notes->vectors keys sort (map name))
                      :selection-mode :single))

;; Text area for a tab.
(def tab-area (label "nth selected"))

;; Initial content for a main frame.
(def main-frame-content
  (left-right-split (scrollable notes-listbox) tab-area :divider-location 1/4))

;; ## GUI itself

(defn gui []
  (invoke-later
   (show-main-frame)))

;; ## Helpers

(defn show-main-frame
  "Shows a GUI with possibility to debug it."
  []
  (display main-frame-content)
  (activate-listeners)
  (selection! notes-listbox "D")
  (show! main-frame))

(defn activate-submit-listener
  "Listens for a click on a submit button"
  []
  (listen submit :action (fn [e] (alert e "Ok!"))))

;; TODO display some graphics, not a vector
(defn activate-notes-list-listener
  "Listens to a change of a selection in a notes list."
  []
  (listen notes-listbox :selection
          (fn [e]
            (when-let [s (selection e)]
              (text! tab-area (str (api/transform-note s)))))))

(defn activate-listeners
  "Activates all listeners required on a app's start."
  []
  (activate-notes-list-listener)
  (activate-submit-listener))

(defn display
  "Displays given content on a main frame."
  [content]
  (config! main-frame :content content)
  content)