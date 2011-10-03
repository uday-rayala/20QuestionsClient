(ns ruk.main (:gen-class)
  (:use ruk.twenty-questions))

(def question-bank {:question "Are you a character from Dr Who?"
                           :yes "Dr Who"
                           :no {:question "Are you a famous athlete?"
                                :yes {:question "Are you a sprinter?"
                                      :yes "Usain Bolt"
                                      :no "Michael Phelps"}
                                :no "Jimmy Carter"}
                           })

(defn -main [& args] (println (start question-bank)))
