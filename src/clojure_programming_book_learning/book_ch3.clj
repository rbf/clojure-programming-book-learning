(ns clojure-programming-book-learning.book-ch3)

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
    (println (conj v 3 3 4 5)) ; [1 2 3 3 3 4 5]
    (println (conj clojure-programming-book-learning.book-ch3/v 3 3 4 5)) ; [a b :name 12.5 3 3 4 5]
    (println (conj s 3 3 4 5)) ; #{1 2 3 4 5}
    (println (conj l 3 3 4 5)) ; (5 4 3 3 1 2 3)
    (println (conj m [3 3] [4 5])) ; {4 5, 3 3, :one 1, :two 2}
    (println "seq")
    (println (seq v)) ; (1 2 3)
    (println (seq s)) ; (1 2 3)
    (println (seq l)) ; (1 2 3)
    (println (seq m)) ; ([:one 1] [:two 2])
    (println "count")
    (println (count v)) ; 3
    (println (count s)) ; 3
    (println (count l)) ; 3
    (println (count m)) ; 2
    (println (count (str *ns*))) ; 42
    (println "=")
    (println (= v [1 2 3])) ; true
    (println (= v [1 3 2])) ; false
    (println (= s #{1 2 3})) ; true
    (println (= s #{1 3 2})) ; true
    (println (= l '(1 2 3))) ; true
    (println (= l '(1 3 2)))v
    (println (= m {:one 1 :two 2})) ; true
    (println (= m { :two 2 :one 1})) ; true
    ))


;=> (doc interleave)
;-------------------------
;clojure.core/interleave
;([c1 c2] [c1 c2 & colls])
;  Returns a lazy seq of the first item in each coll, then the second etc.
;nil

;=> (doc take-nth)
;-------------------------
;clojure.core/take-nth
;([n coll])
;  Returns a lazy seq of every nth item in coll.
;nil

;=> (doc drop)
;-------------------------
;clojure.core/drop
;([n coll])
;  Returns a lazy sequence of all but the first n items in coll.
;nil

(defn swap-pairs
  [sequential]
  (into (empty sequential)
        (interleave
          (take-nth 2 (drop 1 sequential))
          (take-nth 2 sequential))))



;=> (doc hash-map)
;-------------------------
;clojure.core/hash-map
;([] [& keyvals])
;  keyval => key val
;  Returns a new hash map with supplied mappings.
;nil

;=> (doc sorted-map)
;-------------------------
;clojure.core/sorted-map
;([& keyvals])
;  keyval => key val
;  Returns a new sorted map with supplied mappings.
;nil

;=> (doc for)
;-------------------------
;clojure.core/for
;([seq-exprs body-expr])
;Macro
;  List comprehension. Takes a vector of one or more
;   binding-form/collection-expr pairs, each followed by zero or more
;   modifiers, and yields a lazy sequence of evaluations of expr.
;   Collections are iterated in a nested fashion, rightmost fastest,
;   and nested coll-exprs can refer to bindings created in prior
;   binding-forms.  Supported modifiers are: :let [binding-form expr ...],
;   :while test, :when test.
;
;  (take 100 (for [x (range 100000000) y (range 1000000) :while (< y x)] [x y]))
;nil

(def an-unsorted-map (hash-map :one 1 :three 3 :two 2))
(def a-sorted-map (sorted-map :one 1 :three 3 :two 2))

(defn map-map
  [f m]
  (into (empty m)
        (for [[k v] m]
          [k (f v)])))


;; Sequences abstraction (page 89)

(defn try-sequences-functions
  "Trying seq, first, rest, next, lazy-seq"
  []
  (println "seq")
  (println (seq "Clojure")) ; (C l o j u r e)
  (println (seq [])) ; nil
  (println (seq {})) ; nil
  (println (seq ())) ; nil
  (println (seq nil)) ; nil
  
  )


; Page 90: many functions that work with sequences call seq on their arguments

(defn count-used-letters
  "Counts the different letters used in a string"
  [string]
  (count (set (clojure.string/lower-case string))))

#_(defn update-letter-freq
  [m letter]
  (if-let [x (m (keyword (str letter)))]
    (conj m [(keyword (str letter)) (inc x)])
    (conj m [(keyword (str letter)) 1])))

(defn update-letter-freq
  [m letter]
  (let [k (str letter)]
    (conj m [k (inc (or (m k) 0))])))

(defn aggregate-letter-freqs
  [m [first-letter & rest]]
  (let [m2 (update-letter-freq m first-letter)]
    (if rest
      (recur m2 rest)
      m2)))

(defn letter-freq
  "Counts frequency of each letter in a string"
  [string]
  (aggregate-letter-freqs (sorted-map) (re-seq #"[a-z]" (clojure.string/lower-case string))))

; From https://gist.github.com/587176
(defn letter-freq2
  "Counts frequency of each letter in a string"
  [string]
  (frequencies (re-seq #"[a-z]" (clojure.string/lower-case string))))

(def some-freqs {:a 1399, :b 275, :c 542, :d 702, :e 2196, :f 335, :g 312, :h 871, :i 1212, :j 24, :k 127, :l 873, :m 312, :n 1147, :o 1110, :p 396, :q 9, :r 1121, :s 1147, :t 1511, :u 423, :v 227, :w 313, :x 22, :y 252, :z 5})

(defn make-freq-relative
  [m]
  (let [total (apply + (vals m))]
    (into (empty m) (for [[k v] m] [k (* 100 (/ v (float total)))]))))

(defn letter-freq-relative
  "Calculates relative frequency of each letter in a string"
  [string]
  (println (for [[k v] (make-freq-relative (letter-freq string))] (format "\n%s => %5.2f" (name k) v))))



