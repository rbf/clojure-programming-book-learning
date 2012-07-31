(ns clojure-programming-book-learning.core-test
  (:use clojure.test
        clojure-programming-book-learning.core))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(deftest test-ages
  (testing "Ages"
			     (is (= (chas :age) 31))
			     (is (= (meb :age) 41))
			     (is (= (rob :age) 30))))