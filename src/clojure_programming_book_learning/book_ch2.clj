(ns clojure-programming-book-learning.book-ch2)

;; Title: Functional Programming

;; Errata

;; Page 56:
;; - (= five (StatefulInteger. 42))
;; + (= six (StatefulInteger. 42))

;; Page 63:
;; - ;= 10
;; + ;= 48

;; Page 76:                             V
;; - #(logger (format "[%1$tY.%1$tm.%1$te %1$tH:%1$tM:%1$tS] %2$s" (java.util.Date.) %)))
;; + #(logger (format "[%1$tY.%1$tm.%1$td %1$tH:%1$tM:%1$tS] %2$s" (java.util.Date.) %)))
;;                                      ^

;;
                                         
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

(def add2
  (partial + 2))

(def add5
  "=> (add5 5)
  10"
  (partial add2 3))

(def only-strings
  "=> (only-strings [\"a\" \"b\" 3 4 \"d\" 6])
  (\"a\" \"b\" \"d\")"
  (partial filter string?))

(defn test-partial
  []
  (println (#(map * % %2) [1 2] [3 4]))
  (println (#(map * %) [1 2]))
  (println (#(apply map * %&) [1 2] [3 4]))
  (println ((partial map *) [1 2] [3 4])))


;; Function composition (page 68)

(defn negated-sum-string
  [& numbers]
  (str (- (apply + numbers))))

(def negated-sum-string-2 (comp str - +))

(require '[clojure.string :as str])

(def camel->keyword
  "=> (camel->keyword \"CamelCase\")
  :camel-case
  => (camel->keyword \"lowerCamelCase\")
  :lower-camel-case"
  (comp
    keyword
    str/join
    (partial interpose \-)
    (partial map str/lower-case)
    #(str/split % #"(?<=[a-z])(?=[A-Z])")))

(def camel-pairs->map
  "=> (camel-pairs->map [\"CamelCase\" 2 \"lowerCamelCase\" 5])
  {:camel-case 2, :lower-camel-case 5}"
  (comp
    (partial apply hash-map)
    (partial map-indexed (fn [i x]
                           (if (odd? i)
                           x
                           (camel->keyword x))))))


;; Building Higher-Order Functions (HOF) (page 71)

(defn adder
  "=> ((adder 2) 5)
  7"
  [n]
  (fn
    [x]
    (+ n x)))

(def adder-2 (adder 2))

(defn doubler
  [f]
  (fn
    [& numbers]
    (* 2 (apply f numbers))))

(def double-+ (doubler +))


;; Building a primitive loggin system with HOFs (page 72)

(defn logger
  [writer]
  #(binding [*out* writer]
     (println %)))

(def out-logger (logger *out*))

(def writer (java.io.StringWriter.))

(def retained-logger (logger writer))

#_(require 'clojure.java.io)
(require '[clojure.java.io :as io])

(defn file-logger
  [file]
  #(with-open [f (io/writer file :append true)]
     ((logger f) %)))

(def log->file (file-logger "messages.log"))

(defn multi-logger
  [& logger-fns]
  #(doseq [f logger-fns]
     (f %)))

(def log
  "=> (log \"Hello again!\")
  Hello again!
  nil

  % more mesages.log
  Hello again!"
  (multi-logger
	  (logger *out*)
	  (file-logger "messages.log")))


(defn timestamped-logger
  [logger]
  #(logger (format "[%1$tY.%1$tm.%1$td %1$tH:%1$tM:%1$tS] %2$s" (java.util.Date.) %)))

(def timestamped-log
  (timestamped-logger
    (multi-logger
      (logger *out*)
      (file-logger "messages.log"))))


;; Pure Functions (page 76)

(require 'clojure.xml)

(defn twitter-followers
  [username]
  (->> (str "https://api.twitter.com/1/users/show.xml?screen_name=" username)
    clojure.xml/parse
    :content
    (filter (comp #{:followers_count} :tag))
    first
    :content
    first
    Integer/parseInt))

(def twitter-followers2
  (comp
    #(Integer/parseInt %)
    first
    :content
    first
    (partial filter (comp #{:followers_count} :tag))
    :content
    clojure.xml/parse
    #(str "https://api.twitter.com/1/users/show.xml?screen_name=" %)))

(def twitter-followers3
  (comp
    #(Integer/parseInt %)
    #(first %)
    #(:content %)
    #(first %)
    (partial filter (comp #{:followers_count} :tag))
    #(:content %)
    #(clojure.xml/parse %)
    #(str "https://api.twitter.com/1/users/show.xml?screen_name=" %)))

(defn twitter-followers-equivalent
  [username]
  (Integer/parseInt (first (:content (first (filter #(#{:followers_count} (:tag %)) (:content 
     (clojure.xml/parse (str "https://api.twitter.com/1/users/show.xml?screen_name=" username)))))))))


(defn prime?
  [n]
  (cond
    (== 1 n) false
    (== 2 n) true
    (even? n) false
    :else (->> (range 3 (inc (Math/sqrt n)) 2)
            (filter #(zero? (rem n %)))
            empty?)))

(defn test-memoization
  "=> (test-memoization 11018881818881011)
  \"Elapsed time: 5856.705 msecs\"
  \"Elapsed time: 0.012 msecs\"
  true"
  [n]
  (let [m-prime? (memoize prime?)]
    (time (m-prime? n))
    (time (m-prime? n))))

(defn test-memoization-bad
  "(5 8 4 9 8 0 6 2)
  (5 8 4 9 8 0 6 2)
  (3 0 0 3 0 8 4)
  (3 0 0 3 0 8 4)
  (5 8 4 9 8 0 6 2)"
  []'
  (let [m-rands (memoize #(repeatedly % (partial (rand-int) 10)))]
    (println (m-rands 8))
    (println (m-rands 8))
    (println (m-rands 7))
    (println (m-rands 7))
    (println (m-rands 8))
    ))

(defn test-memoization-bad2
  "(0 0 0 0 0 0 0 0)
  (0 0 0 0 0 0 0 0)
  (9 9 9 9 9 9 9)
  (9 9 9 9 9 9 9)
  (0 0 0 0 0 0 0 0)"
  []
  (let [m-rands (memoize #(repeatedly % (partial (memoize rand-int) 10)))]
    (println (m-rands 8))
    (println (m-rands 8))
    (println (m-rands 7))
    (println (m-rands 7))
    (println (m-rands 8))
    ))
