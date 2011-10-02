(ns ruk.twenty-questions-test
  (:use [clojure.test] [ruk.twenty-questions])
  (:use midje.sweet))

(def dr-who-question-bank {:question "Are you a character from Dr Who?" :yes "Dr Who" :no "Jimmy Carter"})
(def question-bank {:question "Are you a character from Dr Who?"
                           :yes "Dr Who"
                           :no {:question "Are you a famous athlete?"
                                :yes {:question "Are you a sprinter?"
                                      :yes "Usain Bolt"
                                      :no "Michael Phelps"}
                                :no "Jimmy Carter"}
                           })

(fact (identify dr-who-question-bank) => (contains {:answer "Dr Who"})
  (provided (ask "Are you a character from Dr Who?") => "Yes"))

(fact (identify dr-who-question-bank) => (contains {:answer "Jimmy Carter"})
  (provided (ask "Are you a character from Dr Who?") => "No"))

(fact (identify question-bank) => (contains {:answer "Usain Bolt"})
  (provided (ask "Are you a character from Dr Who?") => "No"
            (ask "Are you a famous athlete?") => "Yes"
            (ask "Are you a sprinter?") => "Yes"))

(fact (identify question-bank) => (contains {:answer "Jimmy Carter"})
  (provided (ask "Are you a character from Dr Who?") => "No"
            (ask "Are you a famous athlete?") => "No"))

(fact (identify question-bank) => (contains {:answer "Michael Phelps"})
  (provided (ask "Are you a character from Dr Who?") => "No"
            (ask "Are you a famous athlete?") => "Yes"
            (ask "Are you a sprinter?") => "No"))
