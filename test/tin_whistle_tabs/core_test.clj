;; ## Main tests namespace
;; Tests for a tool (mainly, its API).
(ns tin-whistle-tabs.core-test
  (:use [midje.sweet])
  (:require [tin-whistle-tabs.transformation-api :as api]
            [tin-whistle-tabs.interface.cli :as cli]))


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

;; Tests for a abc->tab transformation
;; ABC is from http://www.thesession.org/tunes/display/10#abc
(against-background
 [(around :contents
          (let [butterfly-abc
                (str "X: 1"
                     "T: Butterfly, The\n"
                     "M: 9/8\n"
                     "L: 1/8\n"
                     "R: slip jig\n"
                     "K: Emin\n"
                     "|:B2E G2E F3|B2E G2E FED|B2E G2E F3|B2d d2B AFD:|\n"
                     "|:B2d e2f g3|B2d g2e dBA|B2d e2f g2a|b2a g2e dBA:|\n"
                     "|:B3 B2A G2A|B3 BAB dBA|B3 B2A G2A|B2d g2e dBA:|")
                butterfly-abc-notes
                (str "|:B2E G2E F3|B2E G2E FED|B2E G2E F3|B2d d2B AFD:|\n"
                     "|:B2d e2f g3|B2d g2e dBA|B2d e2f g2a|b2a g2e dBA:|\n"
                     "|:B3 B2A G2A|B3 BAB dBA|B3 B2A G2A|B2d g2e dBA:|")
                butterfly-tab
                [[:x :o :o :o :o :o :-] [:x :x :x :x :x :o :-]
                 [:x :x :x :o :o :o :-] [:x :x :x :x :x :o :-] []
                 [:x :o :o :o :o :o :-] [:x :x :x :x :x :o :-]
                 [:x :x :x :o :o :o :-] [:x :x :x :x :x :o :-] []
                 [:x :x :x :x :x :o :-] [:x :x :x :x :x :x :-]
                 [:x :o :o :o :o :o :-] [:x :x :x :x :x :o :-]
                 [:x :x :x :o :o :o :-] [:x :x :x :x :x :o :-] []
                 [:x :o :o :o :o :o :-] [:o :x :x :x :x :x :+]
                 [:o :x :x :x :x :x :+] [:x :o :o :o :o :o :-]
                 [:x :x :o :o :o :o :-] [] [:x :x :x :x :x :x :-]
                 [:x :o :o :o :o :o :-] [:o :x :x :x :x :x :+]
                 [:x :x :x :x :x :o :+] [] [:x :x :x :o :o :o :+]
                 [:x :o :o :o :o :o :-] [:o :x :x :x :x :x :+]
                 [:x :x :x :o :o :o :+] [:x :x :x :x :x :o :+]
                 [:o :x :x :x :x :x :+] [:x :o :o :o :o :o :-]
                 [:x :x :o :o :o :o :-] [:x :o :o :o :o :o :-]
                 [:o :x :x :x :x :x :+] [:x :x :x :x :x :o :+] []
                 [:x :x :x :o :o :o :+] [:x :x :o :o :o :o :+]
                 [:x :o :o :o :o :o :+] [:x :x :o :o :o :o :+]
                 [:x :x :x :o :o :o :+] [:x :x :x :x :x :o :+]
                 [:o :x :x :x :x :x :+] [:x :o :o :o :o :o :-]
                 [:x :x :o :o :o :o :-] [:x :o :o :o :o :o :-]
                 [:x :o :o :o :o :o :-] [:x :x :o :o :o :o :-]
                 [:x :x :x :o :o :o :-] [:x :x :o :o :o :o :-]
                 [:x :o :o :o :o :o :-] [:x :o :o :o :o :o :-]
                 [:x :x :o :o :o :o :-] [:x :o :o :o :o :o :-]
                 [:o :x :x :x :x :x :+] [:x :o :o :o :o :o :-]
                 [:x :x :o :o :o :o :-] [:x :o :o :o :o :o :-]
                 [:x :o :o :o :o :o :-] [:x :x :o :o :o :o :-]
                 [:x :x :x :o :o :o :-] [:x :x :o :o :o :o :-]
                 [:x :o :o :o :o :o :-] [:o :x :x :x :x :x :+]
                 [:x :x :x :o :o :o :+] [:x :x :x :x :x :o :+]
                 [:o :x :x :x :x :x :+] [:x :o :o :o :o :o :-]
                 [:x :x :o :o :o :o :-]]]
            ?form))]
 (fact "extract notes part from an abc notation"
       (api/extract-notes butterfly-abc) => butterfly-abc-notes)
 (fact "transform butterfly tune abc notation to a transitional format (vector)"
       (api/abc->tab butterfly-abc) => butterfly-tab))

;; ## Tests for a CLI

;; Tests for generation of CLI answer.
(against-background
 [(around :contents (let [test-input-1 [:a :b :C :g]
                          test-input-2 ["o" "5" 8 "r23s"]
                          test-input-3 [98 :a "C#" "f" :L]]
                      ?form))]
 (fact "query with supported notes and nil default-tab"
  (cli/transform-notes test-input-1 nil)
  => (str ":a - [:x :x :o :o :o :o :+]\n"
          ":b - [:x :o :o :o :o :o :+]\n"
          ":C - [:o :x :x :o :o :o :-]\n"
          ":g - [:x :x :x :o :o :o :+]"))
 (fact "query with unsupported notes and error message from api as default-tab"
  (cli/transform-notes test-input-2 api/no-such-note-error)
  => (str "o  - Not a note.\n"
          "5  - Not a note.\n"
          "8  - Not a note.\n"
          "r23s - Not a note."))
 (fact "query with supported and unsupported notes and nil default-tab"
  (cli/transform-notes test-input-3 nil)
  => (str "98 - []\n"
          ":a - [:x :x :o :o :o :o :+]\n"
          "C# - [:o :o :o :o :o :o :-]\n"
          "f  - []\n"
          ":L - []"))) 