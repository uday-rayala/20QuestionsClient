(ns ruk.twenty-questions (:require [clojure.zip :as zip]))

(defn ask [question] (println question) (read-line))

(defn question-zip [questions] (zip/zipper map? (juxt :yes :no) #(assoc %1 :yes (first %2) :no (last %2)) questions))

(defn identify [root]
  (let [answer (ask (:question (zip/node root)))]
    (let [next (if (= "Yes" answer) (-> root zip/down zip/leftmost) (-> root zip/down zip/rightmost))]
      (if (zip/branch? next) (recur next) next))))

(defn update-questions [wrong-answer]
  (let [correct-answer (ask "Who are you?") correct-question (ask "What question should i ask to identify you?")]
    (zip/replace wrong-answer {:question correct-question :yes correct-answer :no (zip/node wrong-answer)})))

(defn confirm [answer]
  (let [confirmation (ask (str "Are you " (zip/node answer) "?"))]
    (if (= "Yes" confirmation) (zip/root answer) (zip/root (update-questions answer)))))

(defn start [questions]
  (let [root (question-zip questions)] 
    (-> root (identify) (confirm))))
