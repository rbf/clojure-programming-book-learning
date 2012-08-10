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
  
  (println "first")
  (println (first "Clojure")) ; C
  (println (first [1])) ; 1
  (println (first {1 2})) ; [1 2]
  (println (first '(1))) ; 1
  (println (first [])) ; nil
  (println (first {})) ; nil
  (println (first ())) ; nil
  (println (first nil)) ; nil
  
  (println "rest")
  (println (rest "Clojure")) ; (l o j u r e)
  (println (rest [1])) ; ()
  (println (rest {1 2})) ; ()
  (println (rest '(1))) ; ()
  (println (rest [])) ; ()
  (println (rest {})) ; ()
  (println (rest ())) ; ()
  (println (rest nil)) ; ()
  
  (println "next")
  (println (next "Clojure")) ; (l o j u r e)
  (println (next [1])) ; nil
  (println (next {1 2})) ; nil
  (println (next '(1))) ; nil
  (println (next [])) ; nil
  (println (next {})) ; nil
  (println (next ())) ; nil
  (println (next nil)) ; nil
  
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


; Sequences are not iterators (page 91)

;=> (doc doseq)
;-------------------------
;clojure.core/doseq
;([seq-exprs & body])
;Macro
;  Repeatedly executes body (presumably for side-effects) with
;  bindings and filtering as provided by "for".  Does not retain
;  the head of the sequence. Returns nil.

(defn try-doseq
  []
  (doseq [x (range 10)]
    (println x))
  (doseq [x (range 4) y (range 2)]
    (println x y))
  ; Compare to for
  (println (for [x (range 4) y (range 2)]
    [x y])))

; Sequences are not lists (page 92)

; A list tracks its lenght, i.e. (count) on a list is a cheap, constant-time operation.
; Obtaining the length of a seq carries a cost since the seq must be fully traversed, and they may be infinite.

(defn compare-seq-and-list
  []
  (let [s (time (range 1e4))] ;"Elapsed time: 0.033 msecs"
    (time (count s))) ; "Elapsed time: 0.891 msecs"
  (let [l (time (apply list (range 1e4)))] ; "Elapsed time: 0.981 msecs"
    (time (count l))) ; "Elapsed time: 0.0020 msecs"
  (let [s (time (range 1e6))] ; "Elapsed time: 0.0080 msecs"
    (time (count s))) ; "Elapsed time: 125.605 msecs"
  (let [l (time (apply list (range 1e6)))] ; "Elapsed time: 1106.038 msecs"
    (time (count l))) ; "Elapsed time: 0.020 msecs"
  )


; Creating seqs (page 92)

;=> (doc cons)
;-------------------------
;clojure.core/cons
;([x seq])
;  Returns a new seq where x is the first element and seq is
;    the rest.

;=> (doc list*)
;-------------------------
;clojure.core/list*
;([args] [a args] [a b args] [a b c args] [a b c d & more])
;  Creates a new list containing the items prepended to the rest, the
;  last of which will be treated as a sequence.

(defn try-seq-creation
  []
  (println (cons 0 (range 1 5))) ; (0 1 2 3 4)
  (println (cons 0 [1 2 3])) ; (0 1 2 3)
  (println (list* 0 1 2 3 4 (range 5 10))) ; (0 1 2 3 4 5 6 7 8 9)
  ; Compare to conj
  (println (conj (range 1 5) 0)) ; (0 1 2 3 4)
  (println (conj (apply list (range 1 5)) 0)) ; (0 1 2 3 4)
  (println (conj [1 2 3] 0)) ; [1 2 3 0]
  (println (conj (range 5 10) 0 1 2 3 4))) ; (4 3 2 1 0 5 6 7 8 9)


; Lazy seqs (page 93)

(defn random-ints
  "Returns a lazy seq of random integers in the range [0, limit)"
  [limit]
  (lazy-seq
    (println "Look, I'm realizing now an item of the lazy-seq!")
    (cons (rand-int limit)
          (random-ints limit))))

(defn random-ints-with-proof
  "Returns a lazy seq of random integers in the range [0, limit)"
  [limit]
  (lazy-seq
    (let [new-random-int (rand-int limit)]
      (println (format "Look, I'm realizing now an item of the lazy-seq! It is the %d" new-random-int))
      (cons new-random-int
            (random-ints-with-proof limit)))))

; (repeatedly) returns a lazy seq applying the argument function,
; so that previous function could in fact be written like follows:

(defn random-ints2
  "Returns a lazy seq of random integers in the range [0, limit)"
  [limit]
  (repeatedly #(rand-int limit)))


; Difference between 'rest' and 'next' (page 96)
; 'rest' blindly returns the tail of a seq, without realization
; 'next' checks for a non-empty tail seq, thus forcing the potential realiation of the tail's head.

;=> (def tail (next (random-ints 50)))
;Look, I'm realizing now an item of the lazy-seq!
;Look, I'm realizing now an item of the lazy-seq!
;#'clojure-programming-book-learning.book-ch3/tail

;=> (def tail (rest (random-ints 50)))
;Look, I'm realizing now an item of the lazy-seq!
;#'clojure-programming-book-learning.book-ch3/tail


; 'doall' ensures a complete realization of the lazy seq while retaining the values
; 'dorun' does the same whitout retaining the values, though (i.e. when caring about side-effects)

;=> (doc dorun)
;-------------------------
;clojure.core/dorun
;([coll] [n coll])
;  When lazy sequences are produced via functions that have side
;  effects, any effects other than those needed to produce the first
;  element in the seq do not occur until the seq is consumed. dorun can
;  be used to force any effects. Walks through the successive nexts of
;  the seq, does not retain the head and returns nil.
;nil

;=> (doc doall)
;-------------------------
;clojure.core/doall
;([coll] [n coll])
;  When lazy sequences are produced via functions that have side
;  effects, any effects other than those needed to produce the first
;  element in the seq do not occur until the seq is consumed. doall can
;  be used to force any effects. Walks through the successive nexts of
;  the seq, retains the head and returns it, thus causing the entire
;  seq to reside in memory at one time.
;nil

(defn try-doall-vs-dorun
  []
  (let [s1 (dorun (take 5 (random-ints 50)))
        s2 (doall (take 5 (random-ints 50)))]
    (println s1)
    (println s2)))

; iterate (page 97)

(defn table-of
  [x]
  (iterate (partial + x) 0))

(defn table-of2
  [x]
  (iterate #(+ x %) 0))

; NB: funtion literal seems more performant than partial:

;=> (time (nth (table-of 2) 1e8))
;"Elapsed time: 26515.02 msecs"
;200000000

;=> (time (nth (table-of2 2) 1e8))
;"Elapsed time: 10251.736 msecs"
;200000000


; Common pattern in Clojure (page 98): extract a sequence from data source,
; process it and turn it back into a more appropiate data structure. Example:

(defn remove-voyels
  [string]
  (apply str (remove (set "aeiou") string)))


; Head retention (page 98)

;=> (doc split-with)
;-------------------------
;clojure.core/split-with
;([pred coll])
;  Returns a vector of [(take-while pred coll) (drop-while pred coll)]
  
(defn try-split-with
  []
  (println (split-with neg? (range -5 5)))
  (println (split-with neg? [-5 -4 -3 0 3 4 -5 -6])))

(defn check-head-retention-oom
  []
  (let [[first-part second-part] (split-with #(< % 12) (range 1e8))]
    [(count second-part) (count first-part)]))

;=> (check-head-retention-oom)
;OutOfMemoryError Java heap space  java.lang.Long.valueOf (Long.java:557)

(defn check-head-retention-no-retention
  []
  (let [[first-part second-part] (split-with #(< % 12) (range 1e8))]
    [(count first-part) (count second-part)]))

;=> (check-head-retention-no-retention)
;[12 99999988]


;; Associative abstraction (page 99)

(defn try-associative-functions
  "Trying assoc, dissoc, get, contains?"
  []
  (let [m {:a 1 :b 2 :c 3}
        v [1 2 3]
        s #{1 2 3}
        l '(1 2 3)]
  (println "Maps are the canonical associative data structure:")
  (println (get m :a)) ; 1
  (println (get m :d)) ; nil
  (println (get m :d "default-value")) ; default-value
  (println (assoc m :d 4)) ; {:d 4, :a 1, :c 3, :b 2}
  (println (assoc m
                  :d 4
                  :e 5
                  :f 6)) ; {:f 6, :e 5, :d 4, :a 1, :c 3, :b 2}
  (println (dissoc m :b)) ; {:a 1, :c 3}
  (println (dissoc m :d)) ; {:a 1, :c 3, :b 2}
  (println (dissoc m :a :c)) ; {:b 2}
  (println (contains? m :a)) ; true
  (println (contains? m :f)) ; false
  
  (println "")  
  (println "Vectors are also associative collections of values with their indexes:")
  (println (get v 1)) ; 1
  (println (get v 10)) ; nil
  (println (get v 10 "default-value")) ; default-value
  (println (assoc v 
                  1 "tada"
                  0 :first
                  2 10)) ; 
  (println (assoc v 3 "you can append a value like conj"))
  #_(println (assoc v 4 "but only at the position (count v)")) ; IndexOutOfBoundsException   clojure.lang.PersistentVector.assocN (PersistentVector.java:136)
  #_(println (dissoc v 1)) ; not supported by vectors: ClassCastException clojure.lang.PersistentVector cannot be cast to clojure.lang.IPersistentMap  clojure.lang.RT.dissoc (RT.java:747)
  (println (contains? v 0)) ; true
  (println (contains? v 3)) ; false

  (println "")  
  (println "Sets support also get (values keyed to themselves)")
  (println (get s 1)) ; 1
  (println (get s 10)) ; nil
  (println (get s 10 "default-value")) ; default-value
  (println (contains? s 0)) ; false
  (println (contains? s 3)))) ; true

(defn contains-value?
  [coll x]
  (boolean (some #{x} (if (map? coll) (vals coll) coll))))


; Beware of 'nil' and 'false' values (page 102)

(defn getting-nil-and-false-real-values
  []
  (let [m {:a false :b nil}]
    (println m) ; {:a false, :b nil}
    (println "(get m :b) =>" (get m :b)) ; (get m :b) => nil
    (println "(get m :b \"default-value\") =>" (get m :b "default-value")) ; (get m :b "default-value") => nil
    (println "(find m :b) =>" (find m :b)) ; (find m :b) => [:b nil]
    (println "(get m :not-existing-key) =>" (get m :not-existing-key)) ; (get m :not-existing-key) => nil
    (println "(get m :not-existing-key \"default-value\") =>" (get m :not-existing-key "default-value")) ; (get m :not-existing-key "default-value") => default-value
    (println "(find m :not-existing-key) =>" (find m :not-existing-key)) ; (find m :not-existing-key) => nil
    
    ; Usage pattern with when-let or if-let:
    (println (if-let [v (get m :a)]
               (format "I found the value of :a => %s" v)
               "I don't find the value of :a... WHY?")) ; I don't find the value of :a... WHY?
    (println (if-let [v (get m :b)]
               (format "I found the value of :b => %s" v)
               "I don't find the value of :b neither!! WHY?")) ; I don't find the value of :b... WHY?
    (println (when-let [e (find m :b)]
               [(key e) (val e)])) ; [:b nil]
    (println (when-let [[k v] (find m :a)]
              (format "Key %s was found and its value is %s" k v))) ; Key :a was found and its value is false
    (println (when-let [[k v] (find m :b)]
              (format "Key %s was found and its value is %s" k v))) ; Key :b was found and its value is null
    (println (if-let [[k v] (find m :not-existing-key)]
               (format "Key %s was found and its value is %s" k v)
               "Key was NOT found!")) ; Key was NOT found!
    
    (println "(get m :a) =>" (get m :a)) ; (get m :a) => false
    (println "(get m :a \"default-value\") =>" (get m :a "default-value")) ; (get m :a "default-value") => false
    ))


;; Indexed abstraction (page 103)

(defn try-indexed-functions
  "Trying nth (and comparing to get)"
  []
  (let [v [:a :b :c]
        m {:a 1 :b 2 :c 3}
        s #{1 2 3}
        l '(1 2 3)]
    (println (nth v 0)) ; :a
    #_(println (nth m 0)) ; UnsupportedOperationException nth not supported on this type: PersistentArrayMap  clojure.lang.RT.nthFrom (RT.java:787)
    #_(println (nth s 0)) ; UnsupportedOperationException nth not supported on this type: PersistentHashSet  clojure.lang.RT.nthFrom (RT.java:787)
    (println (nth l 0)) ; 1
    
    (println (nth v 1)) ; :b
    (println (get v 1)) ; :b
    #_(println (nth v 10)) ; IndexOutOfBoundsException   clojure.lang.PersistentVector.arrayFor (PersistentVector.java:106)
    (println (get v 10)) ; nil
    (println (nth v 10 "default-value")) ; "default-value"
    (println (get v 10 "default-value")) ; "default-value"
    
    ; 'get' is more resilient than 'nth'
    #_(println (nth :something-not-supported 10)) ; UnsupportedOperationException nth not supported on this type: Keyword  clojure.lang.RT.nthFrom (RT.java:787)
    (println (get :something-not-supported 10)) ; nil
    ))


;; Stack abstraction (page 104)

(defn try-stack-functions
  "Trying pop and peek (and comparing to rest and first)"
  []
  (let [v [:a :b :c]
        m {:a 1 :b 2 :c 3}
        s #{1 2 3}
        l '(1 2 3)]
    (println (peek v)) ; :c
    (println (first v)) ; :a
    #_(println (peek m)) ; ClassCastException clojure.lang.PersistentArrayMap cannot be cast to clojure.lang.IPersistentStack  clojure.lang.RT.peek (RT.java:623)
    (println (first m)) ; [:a 1]
    #_(println (peek s)) ; ClassCastException clojure.lang.PersistentHashSet cannot be cast to clojure.lang.IPersistentStack  clojure.lang.RT.peek (RT.java:623)
    (println (first s)) ; 1
    (println (peek l)) ; 1
    (println (first l)) ; 1

    (println (pop v)) ; [:a :b]
    (println (rest v)) ; (:b :c)
    #_(println (pop m)) ; ClassCastException clojure.lang.PersistentArrayMap cannot be cast to clojure.lang.IPersistentStack  clojure.lang.RT.pop (RT.java:629)
    (println (rest m)) ; ([:c 3] [:b 2])
    #_(println (pop s)) ; ClassCastException clojure.lang.PersistentHashSet cannot be cast to clojure.lang.IPersistentStack  clojure.lang.RT.pop (RT.java:629)
    (println (rest s)) ; (2 3)
    (println (pop l)) ; (2 3)
    (println (rest l)) ; (2 3)
    
    (println (peek [])) ; nil
    (println (first [])) ; nil
    #_(println (pop [])) ; IllegalStateException Can't pop empty vector  clojure.lang.PersistentVector.pop (PersistentVector.java:346)
    (println (rest [])) ; ()
    (println (next [])) ; nil

    (println (peek '())) ; nil
    (println (first '())) ; nil
    #_(println (pop '())) ; IllegalStateException Can't pop empty list  clojure.lang.PersistentList$EmptyList.pop (PersistentList.java:178)
    (println (rest '())) ; ()
    (println (next '())) ; nil
    ))


;; Set abstraction (page 105)

(defn try-set-functions
  "Trying disj (and comparing to dissoc)"
  []
  (let [v [:a :b :c]
        m {:a 1 :b 2 :c 3}
        s #{1 2 3}
        l '(1 2 3)]
    #_(println (disj v 1)) ; ClassCastException clojure.lang.PersistentVector cannot be cast to clojure.lang.IPersistentSet  clojure.core/disj (core.clj:1420)
    #_(println (disj m 1)) ; ClassCastException clojure.lang.PersistentArrayMap cannot be cast to clojure.lang.IPersistentSet  clojure.core/disj (core.clj:1420)
    (println (disj s 1)) ; #{2 3}
    #_(println (disj l 1)) ; ClassCastException clojure.lang.PersistentList cannot be cast to clojure.lang.IPersistentSet  clojure.core/disj (core.clj:1420)

    #_(println (dissoc v 1)) ; ClassCastException clojure.lang.PersistentVector cannot be cast to clojure.lang.IPersistentMap  clojure.lang.RT.dissoc (RT.java:747)
    (println (dissoc m :a)) ; {:c 3, :b 2}
    #_(println (dissoc s 1)) ; ClassCastException clojure.lang.PersistentHashSet cannot be cast to clojure.lang.IPersistentMap  clojure.lang.RT.dissoc (RT.java:747)
    #_(println (dissoc l 1)) ; ClassCastException clojure.lang.PersistentList cannot be cast to clojure.lang.IPersistentMap  clojure.lang.RT.dissoc (RT.java:747)
    
	  ;=> (doc clojure.set/subset?)
	  ;-------------------------
	  ;clojure.set/subset?
	  ;([set1 set2])
	  ;  Is set1 a subset of set2?
    (println (clojure.set/subset? #{1 3} s)) ; true
    (println (clojure.set/subset? #{1 3 2} s)) ; true
    (println (clojure.set/subset? #{1 3 4} s)) ; false
    ; and also superset? union, intersection, project...
   
    ; => (doc clojure.set/union)
    ;-------------------------
    ;clojure.set/union
    ;([] [s1] [s1 s2] [s1 s2 & sets])
    ;  Return a set that is the union of the input sets
    (println (clojure.set/union #{3 4 5} s)) ; #{1 2 3 4 5}
    (println (into s [3 4 5])) ; #{1 2 3 4 5}
    (println (conj s 3 4 5)) ; #{1 2 3 4 5}
    
   
    ))





