(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "ALB" 0)
        s (registry/register-submit "eng-1" "ALB" 0)]
    (is (= "ALB-DFT-000000" (get d "draft_number")))
    (is (= "ALB-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "ALB" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest tax-arrears-de-minimis-threshold-is-a-flat-constant
  (testing "Neni 76(2)(c): a flat ALL 10,000, independent of turnover or any other field"
    (is (== 10000.0 registry/tax-arrears-de-minimis-threshold-all))))

(deftest tax-arrears-exceeds-de-minimis
  (testing "arrears below the flat threshold -> does not exceed"
    (is (false? (registry/tax-arrears-exceeds-de-minimis? {:tax-arrears-amount 5000}))))
  (testing "arrears exactly at the flat threshold -> does not exceed"
    (is (false? (registry/tax-arrears-exceeds-de-minimis? {:tax-arrears-amount 10000}))))
  (testing "arrears above the flat threshold -> exceeds (Neni 76(2) mandatory disqualification ground)"
    (is (true? (registry/tax-arrears-exceeds-de-minimis? {:tax-arrears-amount 15000}))))
  (testing "no arrears declared -> never exceeds"
    (is (false? (registry/tax-arrears-exceeds-de-minimis? {})))))
