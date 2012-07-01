(ns tin-whistle-tabs.core-test
  (:use [midje.sweet]
        [tin-whistle-tabs.core]))

(fact "addition has a unit element"
      (+ 12345 0) => 12345)

(fact "checking basic arithmetic"
      (+ 1 2) => 3
      (- 5 3) => 2
      (* 3 4) => 12
      (/ 16 2) => 8)