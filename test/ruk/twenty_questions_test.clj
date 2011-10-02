(ns ruk.twenty-questions-test
  (:use [clojure.test] [ruk.twenty-questions])
  (:use midje.sweet))

(def dr-who-question-bank {:question "Are you a character from Dr Who?" :yes "Dr Who" :no "Jimmy Carter"})

(fact (identify dr-who-question-bank) => "Dr Who"
  (provided (ask "Are you a character from Dr Who?") => "Yes"))

(fact (identify dr-who-question-bank) => "Jimmy Carter"
  (provided (ask "Are you a character from Dr Who?") => "No"))

(fact (identify dr-who-question-bank) => "Jimmy Carter"
  (provided (ask "Are you a character from Dr Who?") => "No"))
