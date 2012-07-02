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
(ns tin-whistle-tabs.transformation-api
  (:require [clojure.string :as string]))


;; ## Declarations
(declare notes->vectors)
(declare no-such-note-error)
(declare extract-notes)

;; ## Note transformation API

(defn transform-note
  "Transforms a given note to a transitional format. Note could be a keyword or a string. Throws an IllegalArgumentException if argument is an unsupported note."
  [note]
  (if-let [note-fingering (notes->vectors (keyword note))]
    note-fingering
    (throw (IllegalArgumentException. no-such-note-error))))

(defn transform-notes
  "Transforms every note in a given sequence to a transitional format. Unsupported notes are transformed to empty vectors."
  [notes]
  (map #(try
          (transform-note %)
          (catch IllegalArgumentException e []))
       notes))

;; TODO take key of tune into account (insert sharps where required)
;; TODO correctly process sharps (more than a character)
;; TODO take bars into account
;; TODO take note duration into account
(defn abc->tab
  "Creates a list with tabs for a given string with an ABC notation of a tune."
  [abc]
  (when-not (nil? abc)
    ; extract a sequence of strings each of which is a letter that
    ; corresponds to a note
    (let [notes-seq
          (map str
           (-> (extract-notes abc)              ; extract notes part
               (string/replace #"[^a-zA-Z]" "") ; leave only letters-notes
               seq))]                           ; create sequence of characters
      (transform-notes notes-seq))))

;; ## Fingering chart

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
   :d    [:o :x :x :x :x :x :+]
   :e    [:x :x :x :x :x :o :+]
   :f#   [:x :x :x :x :o :o :+]
   :g    [:x :x :x :o :o :o :+]
   :a    [:x :x :o :o :o :o :+]
   :b    [:x :o :o :o :o :o :+]
   :c    [:o :x :x :o :o :o :+]
   :c#   [:o :o :o :o :o :o :+]})

;; Message for an unsupported notes.
(def no-such-note-error "Not a note.")

;; ## Utility functions

(defn extract-notes
  "Extracts a string with a notes part from an ABC tune notation."
  [abc]
  (when-not (nil? abc)
    ; in ABC notation notes start after the line with K: ...
    (last (string/split abc #"K:.*\n"))))