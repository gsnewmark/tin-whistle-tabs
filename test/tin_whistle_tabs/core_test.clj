;; ## Main tests namespace
;; Tests for a tool (mainly, its API).
(ns tin-whistle-tabs.core-test
  (:use [midje.sweet]
        [tin-whistle-tabs.core])
  (:require [tin-whistle-tabs.transformation-api :as api]))


;; ## Testing a note transformation API

;; Tests for a transform-note.
(tabular "Transformation of a note to a transitional format."
         (fact (api/transform-note ?note) => ?transformed)
         ?note ?transformed
         :D    [:x :x :x :x :x :x :-]
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
         :c#   [:o :o :o :o :o :o :+]
         "D"   [:x :x :x :x :x :x :-]
         "E"   [:x :x :x :x :x :o :-]
         "F#"  [:x :x :x :x :o :o :-]
         "G"   [:x :x :x :o :o :o :-]
         "A"   [:x :x :o :o :o :o :-]
         "B"   [:x :o :o :o :o :o :-]
         "C"   [:o :x :x :o :o :o :-]
         "C#"  [:o :o :o :o :o :o :-]
         "d"   [:o :x :x :x :x :x :+]
         "e"   [:x :x :x :x :x :o :+]
         "f#"  [:x :x :x :x :o :o :+]
         "g"   [:x :x :x :o :o :o :+]
         "a"   [:x :x :o :o :o :o :+]
         "b"   [:x :o :o :o :o :o :+]
         "c"   [:o :x :x :o :o :o :+]
         "c#"  [:o :o :o :o :o :o :+]
         "5"   (throws IllegalArgumentException)
         "f 3" (throws IllegalArgumentException)
         "f"   (throws IllegalArgumentException)
         :y    (throws IllegalArgumentException))

;; Tests for a transform-notes.
(tabular "Transformation of the notes to a transitional format."
         (fact (api/transform-notes ?notes) => ?transformed)
         ?notes         ?transformed
         ["a" "B" "C#"] [[:x :x :o :o :o :o :+] [:x :o :o :o :o :o :-]
                         [:o :o :o :o :o :o :-]]
         [:D :b :F#]    [[:x :x :x :x :x :x :-] [:x :o :o :o :o :o :+]
                         [:x :x :x :x :o :o :-]]
         [:E]           [[:x :x :x :x :x :o :-]]
         []             []
         ["c" "k"]      [[:o :x :x :o :o :o :+] []]
         ["g" "H" "G"]  [[:x :x :x :o :o :o :+] [] [:x :x :x :o :o :o :-]])
