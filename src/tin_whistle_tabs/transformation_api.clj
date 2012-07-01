;; ## Tool's API namespace
;; Functions that transforms a note to a transitional format.
;; Note must be in a ABC-like notation. 
;; Supported notes: D E F# G A B C C# d e f# g a b c c#
;; Format itself is defined as a list with 7 elements: first six
;; represents a whistle's holes (:o if opened, :x if closed), last -
;; whether the user must overblow (if not - :-, else - :+).
(ns tin-whistle-tabs.transformation-api)

(declare transform-note)