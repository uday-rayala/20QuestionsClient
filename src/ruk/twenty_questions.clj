(ns ruk.twenty-questions (:require [clojure.zip :as zip]))

(defn ask [question] "Yes")
(defn identify [question-bank]
  (let [question-zip (zip/zipper map? (fn [node] [(:yes node) (:no node)]) (fn [node children] (:question node)) question-bank)]
     (let [answer (ask (:question (zip/node question-zip)))]
       (if (= "Yes" answer) (-> question-zip zip/down zip/leftmost zip/node) (-> question-zip zip/down zip/rightmost zip/node)))))
