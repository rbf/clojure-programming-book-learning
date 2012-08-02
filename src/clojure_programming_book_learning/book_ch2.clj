(ns clojure-programming-book-learning.book-ch2)

;; Errata

;; Page 56:
;; - (= five (StatefulInteger. 42))
;; + (= six (StatefulInteger. 42))

;; Page 63:
;; - ;= 10
;; + ;= 48


;; Higher-order functions (HOFs) (from page 61 on)

(defn test-map
  "Testing the map HOF."
  []
  (println (map clojure.string/lower-case ["Java" "CamelCase" "PEACE!"]))
  (println (map * [1 2 3 4] [5 6 7 8]))
  (println (map * [1 2 3 4] [5 6])))

(defn test-reduce
  "Testing the reduce HOF."
  []
  (println (reduce max [0 -1 3 4 -5]))
  (println (reduce conj [] [1 2 3 4]))
  (println (reduce (fn [m v] (assoc m v (* v v))) {} [1 2 3 4]))
  (println (reduce #(assoc % %2 (* %2 %2)) {} [1 2 3 4])))


;; Applying and partially applying funtions (page 65)

(def params [1 2 3])

(defn test-apply
  "Testing apply function."
  []
  (println (apply + [1 2 3 4]))
  (println (apply + '(1 2 3 4 5)))
  #_(println (+ params)) ; Gives a ClassCastException
  (println (apply + params))
  (println (apply + 100 50 params)))

(defn multiplicator
  [a b]
  (* a b))

(def multiplicator-2
  (partial multiplicator 2))

(def multiplicator-3
  (partial multiplicator 3))



