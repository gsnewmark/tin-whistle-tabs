;; ## GUI namespace.
(ns tin-whistle-tabs.interface.gui
  (:use [tin-whistle-tabs.interface.common]
        [seesaw.core]
        [seesaw.chooser]
        [tin-whistle-tabs.interface.cli :only [abc->tab]])
  (:require [tin-whistle-tabs.transformation-api :as api]))


;; ## Declarations
(declare show-main-frame activate-listeners display)

;; ## GUI elements

;; TODO add ids and use selectors

;; App's main window.
(def main-frame (frame :title title
                       :width 200
                       :height 200
                       :resizable? false))

;; Notes listbox.
;; TODO show notes in a correct 'whistle' order
(def notes-listbox
  (listbox :model (->> api/notes->vectors keys sort (map name))
                      :selection-mode :single))

;; Text area for a tab.
(def tab-area (label "nth selected"))

;; Tab with a note to tab converter.
(def note-to-tab-window
  (left-right-split (scrollable notes-listbox) tab-area :divider-location 1/4))

;; Submit button.
(def submit (button :text "Choose file"))

;; Text for a tune's tab.
(def tune-tab-area (text :text "nothing" :multi-line? true :editable? false))

;; Tab with a file to tab converter.
(def file-to-tab-window
  (top-bottom-split submit (scrollable tune-tab-area) :divider-location 1/5))

;; Root element for a frame.
(def container (tabbed-panel
                :tabs [{:title "Note-to-Tab"
                        :tip "Translate a note to a whistle tab."
                        :content note-to-tab-window}
                       {:title "File-to-Tab"
                        :tip "Creates a tab for a given ABC-file."
                        :content file-to-tab-window}]))

;; ## GUI itself

(defn gui []
  (invoke-later
   (show-main-frame)))

;; ## Helpers

(defn show-main-frame
  "Shows a GUI with possibility to debug it."
  []
  (native!)
  (display container)
  (activate-listeners)
  (selection! notes-listbox "D")
  (show! main-frame))

(defn file-explorer
  "Chooses an ABC file for consecutive tab generation."
  []
  (choose-file
   main-frame
   :type :open
   :filters [["ABC" ["abc"]]]
   :success-fn
   (fn [fc file]
     (text!
      tune-tab-area
      (abc->tab [(.getAbsolutePath file)] "[ ]")))))

(defn activate-submit-listener
  "Listens for a click on a submit button"
  []
  (listen submit :action (fn [e] (file-explorer))))

;; TODO display some graphics, not a vector
(defn activate-notes-list-listener
  "Listens to a change of a selection in a notes list."
  []
  (listen notes-listbox :selection
          (fn [e]
            (when-let [s (selection e)]
              (text! tab-area (str (api/transform-note s)))))))

(defn activate-listeners
  "Activates all listeners required on an app's start."
  []
  (activate-notes-list-listener)
  (activate-submit-listener))

(defn display
  "Displays given content on a main frame."
  [content]
  (config! main-frame :content content)
  content)