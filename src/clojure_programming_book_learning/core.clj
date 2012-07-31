(ns clojure-programming-book-learning.core
  #_(:gen-class))

;; Destructuring (page 34)

(def chas {:name "Chas" :age 31 :location "Massachussets"})
(def meb {:name "Meb" :age 41 :location "Thoiry"})
(def rob {:name "Rob" :age 30 :location "Zurich"})

(def people [meb rob chas])

(defn print-chas
  []
  (let [{name :name age :age location :location} chas]
    (format "%s is %s years old and lives in %s" name age location)))

(defn print-person
  [person]
  (let [{:keys [name location age]} person]
    (println (format "%s is %s years old and lives in %s" name age location))))


;; Defining functions (page 37)

(defn strange-adder
  ([x] (strange-adder x 1))
  ([x y] (+ x y)))

(defn concat-rest
  [x & rest]
  (apply str (butlast rest)))

(defn make-user
  "=> (make-user)
  {:user-id \"66040cfe-5201-4230-8c7d-95cbbee5f6c1\"}
  => (make-user \"rob\")
  {:user-id \"rob\"}"
  [& [user-id]]
  {:user-id (or user-id
                (str (java.util.UUID/randomUUID)))})

(defn make-user-ext

  "=> (make-user-ext \"Bobby\")
  {:username \"Bobby\",
   :join-date #inst \"2012-07-30T12:51:32.503-00:00\",
   :email nil,
   :exp-date #inst \"2012-08-29T12:51:32.503-00:00\"}
  => (make-user-ext \"Bobby\"
                  :join-date (java.util.Date. 111 0 1)
                  :email \"bobby@example.com\")
  {:username \"Bobby\",
   :join-date #inst \"2010-12-31T23:00:00.000-00:00\",
   :email \"bobby@example.com\",
   :exp-date #inst \"2011-01-30T23:00:00.000-00:00\"}"

  [username & {:keys [email join-date]
               :or {join-date (java.util.Date.)}}]
  {:username username
   :join-date join-date
   :email email
   ;;2.592e9 -> one month in ms
   :exp-date (java.util.Date. (long (+ 2.592e9 (.getTime join-date))))})

(defn make-user-from-person
  [person]
  (make-user-ext (person :name)
                 :email (clojure.string/join [(clojure.string/lower-case (person :name)) "@example.com"])))

;; Conditionals (page 42)

(defn test-conditionals
  []
  (println (if "hi" :yes :no))
  (println (if 1 :yes :no))
  (println (if 0 :yes :no))
  (println (if false :yes :no))
  (println (if nil :yes :no))
  (println (if (not true) :yes))
  (println "Testing if true")
  (if true (println "1") (println "2"))  
  (println "Testing if false")
  (if false (println "1") (println "2"))  
  (println "Testing when true")
  (when true (println "1") (println "2"))  
  (println "Testing when false")
  (when false (println "1") (println "2"))
  (println "Testing cond first false, second true")
  (cond
    false (println "1")
    true (println "2"))
  (println "Testing cond first true, second true")
  (cond
    true (println "1")
    true (println "2"))  
  (println "Testing cond first false, second false")
  (cond
    false (println "1")
    false (println "2"))  
  (println "END")  
  )


;; Looping: loop and recur (page 43)

(defn test-loop
  []
  (loop [x 5]
    (println x)
    (if (neg? x)
      x
      (recur (dec x)))))

(defn countdown
  [x]
  (if (zero? x)
    :tada!
    (do
      (println x)
      (recur (dec x)))))

(defn fibo
  "Returns the n-th number in the Fibonnacci sequence."
  [n]
  (cond
    (neg? n) nil
    (= 0 n) 0
    (= 1 n) 1
    :else (+ (fibo (dec n)) (fibo (dec (dec n))))))

(defn fibos
  "Returns a seq of the n-th Fibonnacci numbers"
  [n]
  (loop
    [m 0
     fibos []]
    (if (= m n)
      (conj fibos (fibo m))
      (recur (inc m) (conj fibos (fibo m))))))

; From http://clojuredocs.org/clojure_core/clojure.core/lazy-seq
(defn sum-last-2
   ([] (sum-last-2 1 2))
   ([n m] (cons n (lazy-seq (sum-last-2 m (+ n m))))))


;; Naive REPL implementation (page 47)

(defn naive-REPL
  "Naive REPL implementation. Type :quit to exit."
  []
  (print (str (ns-name *ns*) ">>> "))
  (flush)
  (let
    [expr (read)
     val (eval expr)]
    (when (not= val :quit)
      (println val)
      (recur))))


;; Main

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!")
  (when (= "REPL" (first args))
    (naive-REPL))
  (map print-person people)
  (map make-user-from-person people))
