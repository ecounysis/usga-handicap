(ns usga-handicap.core)

(def out-yards 2200)
(def in-yards 1016)
(def epy-adj-in 0)
(def epy-adj-out 0)
(def obstacle-stroke-value-scratch-in 0)
(def obstacle-stroke-value-scratch-out 0)
(def obstacle-stroke-value-bogey-in 0)
(def obstacle-stroke-value-bogey-out 0)

(def obstacle-stroke-value-scratch (+ obstacle-stroke-value-scratch-in obstacle-stroke-value-scratch-out))
(def obstacle-stroke-value-bogey (+ obstacle-stroke-value-bogey-in obstacle-stroke-value-bogey-out))


(defn yardage-rating-function [divisor addend] 
     (fn [eff-playing-yardage]  (+ (/ eff-playing-yardage divisor) addend)))

(defn scratch-yardage-rating [eff-playing-yardage] 
      ((yardage-rating-function 220.0 40.9) eff-playing-yardage))

(defn scratch-yardage-rating-9 [eff-playing-yardage] 
      ((yardage-rating-function 220.0 (/ 40.9 2)) eff-playing-yardage))

(scratch-yardage-rating-9 2200)

(defn bogey-yardage-rating [eff-playing-yardage] 
      ((yardage-rating-function 160.0 50.7) eff-playing-yardage))

(defn bogey-yardage-rating-9 [eff-playing-yardage] 
      ((yardage-rating-function 160.0 (/ 50.7 2)) eff-playing-yardage))

(defn rating [yardage-rating obstacle-stroke-value] 
      (+ yardage-rating obstacle-stroke-value))

(def effective-playing-yardage 
     (+ out-yards in-yards epy-adj-out epy-adj-in))

(def yardage-rating-scratch 
     (scratch-yardage-rating effective-playing-yardage))

(def yardage-rating-bogey 
     (bogey-yardage-rating effective-playing-yardage))

(def usga-course-rating 
     (rating yardage-rating-scratch obstacle-stroke-value-scratch))

(def bogey-course-rating 
     (rating yardage-rating-bogey obstacle-stroke-value-bogey))

(defn slope-rating [bogey-rating usga-course-rating] 
      (* 5.381 (- bogey-rating usga-course-rating)))

(defn slope-rating-9 [bogey-rating-9 usga-course-rating-9] 
      (* (* 5.381 2) (- bogey-rating-9 usga-course-rating-9)))

(def the-slope (slope-rating bogey-course-rating usga-course-rating))