;; ## Tool's API namespace
;; Functions that transforms a note to a transitional format.
;;
;; Note must be in a ABC-like notation.
;;
;; Supported notes: D E F# G A B C C# d e f# g a b c c#
;;
;; Format itself is defined as a list with 7 elements: first six elements
;; represent a whistle's holes (:o if opened, :x if closed), last -
;; whether the user must overblow (if not - :-, else - :+).
;;
;; For example, F# would be represented as a [:x :x :x :x :o :o :-].
(ns tin-whistle-tabs.transformation-api)


;; ## Declarations
(declare notes->vectors)

;; ## Note transformation API

;; TODO 'sad' path - raise an exception
(defn transform-note
  "Transforms a given note to a transitional format. Note could be a keyword or a string."
  [note]
  (notes->vectors (keyword note)))

(defn transform-notes
  "Transforms every note in a given sequence to a transitional format."
  [notes]
  (map transform-note notes))

;; ## Fingering chart

;; TODO remove +\- from fingering, find it from note's capitalization
;; TODO remove fingerings for high octave
;; Map with the pairs "note - its fingering".
(def notes->vectors
  {:D    [:x :x :x :x :x :x :-]
   :E    [:x :x :x :x :x :o :-]
   :F#   [:x :x :x :x :o :o :-]
   :G    [:x :x :x :o :o :o :-]
   :A    [:x :x :o :o :o :o :-]
   :B    [:x :o :o :o :o :o :-]
   :C    [:o :x :x :o :o :o :-]
   :C#   [:o :o :o :o :o :o :-]
   :d    [:x :x :x :x :x :x :+]
   :e    [:x :x :x :x :x :o :+]
   :f#   [:x :x :x :x :o :o :+]
   :g    [:x :x :x :o :o :o :+]
   :a    [:x :x :o :o :o :o :+]
   :b    [:x :o :o :o :o :o :+]
   :c    [:o :x :x :o :o :o :+]
   :c#   [:o :o :o :o :o :o :+]})