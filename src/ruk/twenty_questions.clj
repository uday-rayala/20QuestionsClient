(ns ruk.twenty-questions (:require [clojure.zip :as zip]))

(defn ask [question] "Yes")

(defn trace [question-root]
  (let [answer (ask (:question (zip/node question-root)))]
    (let [next-move (if (= "Yes" answer) (-> question-root zip/down zip/leftmost) (-> question-root zip/down zip/rightmost))]
      (if (zip/branch? next-move) (recur next-move) (zip/node next-move)))))

(defn identify [question-bank]
  (let [question-zip (zip/zipper map? (fn [node] [(:yes node) (:no node)]) (fn [node children] (:question node)) question-bank)]
     (trace question-zip)))
