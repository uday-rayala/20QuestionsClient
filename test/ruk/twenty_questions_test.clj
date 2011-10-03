(ns ruk.twenty-questions-test
  (:use [clojure.test] [ruk.twenty-questions])
  (:use midje.sweet)
  (:require [clojure.zip :as zip]))

(def dr-who-questions {:question "Are you a character from Dr Who?" :yes "Dr Who" :no "Jimmy Carter"})
(def athlete-questions {:question "Are you a character from Dr Who?"
                           :yes "Dr Who"
                           :no {:question "Are you a famous athlete?"
                                :yes "Michael Phelps"
                                :no "Jimmy Carter"}
                           })
(def sample-questions {:question "Are you a character from Dr Who?"
                           :yes "Dr Who"
                           :no {:question "Are you a famous athlete?"
                                :yes {:question "Are you a sprinter?"
                                      :yes "Usain Bolt"
                                      :no "Michael Phelps"}
                                :no "Jimmy Carter"}
                           })

(def sample-question-root (question-zip sample-questions))

(defn answer? [answer] (fn [x] (= answer (zip/node x))))
(fact (identify (question-zip dr-who-questions)) => (answer? "Dr Who")
  (provided (ask "Are you a character from Dr Who?") => "Yes"))

(fact (identify (question-zip dr-who-questions)) => (answer? "Jimmy Carter")
  (provided (ask "Are you a character from Dr Who?") => "No"))

(fact (identify sample-question-root) => (answer? "Usain Bolt")
  (provided (ask "Are you a character from Dr Who?") => "No"
            (ask "Are you a famous athlete?") => "Yes"
            (ask "Are you a sprinter?") => "Yes"))

(fact (identify sample-question-root) => (answer? "Jimmy Carter")
  (provided (ask "Are you a character from Dr Who?") => "No"
            (ask "Are you a famous athlete?") => "No"))

(fact (identify sample-question-root) => (answer? "Michael Phelps")
  (provided (ask "Are you a character from Dr Who?") => "No"
            (ask "Are you a famous athlete?") => "Yes"
            (ask "Are you a sprinter?") => "No"))

(fact (let [dr-who (-> sample-question-root zip/down zip/leftmost)]
        (confirm dr-who)) => sample-questions
      (provided (ask "Are you Dr Who?") => "Yes"))

(fact (let [dr-who (-> (question-zip dr-who-questions) zip/down zip/rightmost)]
        (confirm dr-who)) => athlete-questions
      (provided (ask "Are you Jimmy Carter?") => "No"
                (ask "Who are you?") => "Michael Phelps"
                (ask "What question should i ask to identify you?") => "Are you a famous athlete?"))
