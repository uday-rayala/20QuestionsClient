(ns ruk.twenty-questions (:require [clojure.zip :as zip]))

(defn ask [question] "Yes")

(defn trace [root]
  (let [answer (ask (:question (zip/node root)))]
    (let [next (if (= "Yes" answer) (-> root zip/down zip/leftmost) (-> root zip/down zip/rightmost))]
      (if (zip/branch? next) (recur next) {:answer (zip/node next)}))))

(defn question-zip [question-bank] (zip/zipper map? (fn [node] [(:yes node) (:no node)]) (fn [node children] (:question node)) question-bank))

(defn identify [question-bank] 
  (let [root (question-zip question-bank)] 
    (trace root)))
