(ns clojure-progqramming-book-learning.book-ch3)

;; Title: Collections and Data Structures
;; Page 83


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Errata

;; Page 84 (footnote 2)
;; - "conjoin,"
;; + "conjoin",


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



(def lst '(a b :name 12.5))
(def v ['a 'b :name 12.5])
(def m {:name "Rob" :age 30})
(def s #{1 2 3})

;(def a-bad-map {:name "Someone" :age})
;RESULT:
;CompilerException java.lang.RuntimeException: Map literal must contain an even number of forms, compiling:(clojure_programming_book_learning/book_ch3.clj:11) 

;(def a-bad-map {:name "Someone" :name 21})
;RESULT:
;CompilerException java.lang.IllegalArgumentException: Duplicate key: :name, compiling:(clojure_programming_book_learning/book_ch3.clj:15) 

;(def a-bad-set #{1 2 3 1})
;RESULT:
;CompilerException java.lang.IllegalArgumentException: Duplicate key: 1, compiling:(clojure_programming_book_learning/book_ch3.clj:15)


(defn try-conj
  "Result:
  (3 2 1 a b :name 12.5)
  [a b :name 12.5 1 2 3 12.5]
  {:age 30, 1 2, 3 4, :name Rob}
  {:age 30, 1 2, :name Robert}
  #{1 2 3}
  #{1 2 3 4 5 6}
  #{1 2 3 4 5 6}"
  []
  (println (conj lst 1 2 3))
  (println (conj v 1 2 3 12.5))
  #_(println (conj m 1 2 3 12.5)) ; IllegalArgumentException Don't know how to create ISeq from: java.lang.Long  clojure.lang.RT.seqFrom (RT.java:494)
  (println (conj m [1 2] [3 4]))
  (println (conj m [1 2] [:name "Robert"]))
  (println (conj s 1 2 3))
  (println (conj s 1 2 3 4 5 6))
  (println (conj s 4 5 6)))

(defn try-seq
  "Result:
  (a b :name 12.5)
  (a b :name 12.5)
  ([:age 30] [:name Rob])
  (1 2 3)"
  []
  (println (seq lst))
  (println (seq v))
  (println (seq m))
  (println (seq s)))

(defn try-into-vs-conj
  "Result:
  Vector:
  [a b :name 12.5 [1 2 3]]
  [a b :name 12.5 1 2 3]
  [a b :name 12.5 [a b :name 12.5]]
  [a b :name 12.5 a b :name 12.5]
  Set:
  #{1 [1 2 3] 2 3}
  #{1 2 3}
  #{1 2 3 #{1 2 3}}
  #{1 2 3}
  List:
  ([1 2 3] a b :name 12.5)
  (3 2 1 a b :name 12.5)
  ((a b :name 12.5) a b :name 12.5)
  (12.5 :name b a a b :name 12.5)
  Map:
  {:age 30, 1 2, :name Rob}
  {:age 30, 1 2, 3 4, :name Rob}
  {:age 30, :name Rob}
  {:age 30, :name Rob}
  {:age 29, :name Robert}
  {:age 29, :name Robert}
  nil"
  []
  (println "Vector:")
  (println (conj v [1 2 3]))
  (println (into v [1 2 3]))
  (println (conj v v))
  (println (into v v))
  (println "Set:")
  (println (conj s [1 2 3]))
  (println (into s [1 2 3]))
  (println (conj s s))
  (println (into s s))
  (println "List:")
  (println (conj lst [1 2 3]))
  (println (into lst [1 2 3]))
  (println (conj lst lst))
  (println (into lst lst))
  (println "Map:")
  #_(println (conj m [1 2 3])) ;IllegalArgumentException Vector arg to map conj must be a pair  clojure.lang.APersistentMap.cons (APersistentMap.java:34)
  (println (conj m [1 2]))
  #_(println (into m [1 2 3])) ;IllegalArgumentException Don't know how to create ISeq from: java.lang.Long  clojure.lang.RT.seqFrom (RT.java:494)
  (println (into m [{1 2} {3 4}]))
  (println (conj m m))
  (println (into m m))
  (println (conj m {:age 29, :name "Robert"}))
  (println (into m {:age 29, :name "Robert"}))
  )


;; Collections abstraction (page 87)

(defn try-collection-functions
  "Trying conj, seq, count, empty, ="
  []
  (let [v [1 2 3]
        s #{1 2 3}
        l '(1 2 3)
        m {:one 1 :two 2}]
    (println "conj")
    (println (conj v 3 3 4 5))
    (println (conj clojure-programming-book-learning.book-ch3/v 3 3 4 5))
    (println (conj s 3 3 4 5))
    (println (conj l 3 3 4 5))
    (println (conj m [3 3] [4 5]))
    (println "seq")
    (println (seq v))
    (println (seq s))
    (println (seq l))
    (println (seq m))
    (println "count")
    (println (count v))
    (println (count s))
    (println (count l))
    (println (count m))
    ))


