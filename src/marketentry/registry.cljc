(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `tax-arrears-de-minimis-threshold-all` / `tax-arrears-exceeds-de-
  minimis?` are the SAME discipline applied to a genuinely Albania-
  specific statutory rule: Ligji Nr. 162/2020 'Për Prokurimin Publik'
  (Law No. 162/2020 'On Public Procurement', datë 23.12.2020) Neni 76
  paragraph 2 makes unpaid taxes/obligatory social-security
  contributions a mandatory disqualification ground, but paragraph 2
  letter 'c' carves out an exception where 'the economic operator has
  not paid small amounts of taxes or contributions in social insurance
  up to the value of ALL 10,000' (WebFetch-verified 2026-07-21 directly
  against the Public Procurement Agency's OWN official English
  translation document, downloaded from app.gov.al's legislation page,
  'PP Law 162-2020 English').

  This is a GENUINELY DIFFERENT check SHAPE than both prior iso3166
  siblings this repo mirrors: Bulgaria's ЗОП Art. 54(5) de-minimis is a
  COMPUTED formula (1% of the operator's own annual turnover, capped at
  BGN 50,000) and Azerbaijan's flagship check is a plain BOOLEAN
  registry-membership read. Albania's Neni 76(2)(c) carve-out is a FLAT
  STATUTORY CONSTANT (ALL 10,000) that does not depend on the
  operator's turnover or any other engagement field at all -- so the
  'recompute' here is a plain comparison against a fixed amount, not a
  formula evaluated over other engagement data. This is reported
  honestly as a third distinct check shape for the family, not treated
  as a lesser version of Bulgaria's formula.

  Neni 76(2) actually lists FOUR exceptions to the disqualification
  ground (letter a: unresolved litigation with no final decision;
  letter b: the operator has paid or signed a binding payment
  agreement before the procurement notice; letter c: the ALL 10,000
  de-minimis amount modeled here; letter ç: the operator was notified
  of the amount too late to act). This namespace models ONLY the
  numeric de-minimis carve-out (letter c) -- the litigation-status and
  payment-plan exceptions (letters a, b, ç) are not represented in this
  actor's simplified engagement schema, the same honest-scope-narrowing
  discipline sibling actors apply to their own jurisdictions' fuller
  legal text.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal. It builds the RECORD an
  operator would keep, not the act of submitting a portal registration
  itself (that is `marketentry.operation`'s `:filing/submit`, always
  human-gated -- see README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(def tax-arrears-de-minimis-threshold-all
  "Ligji Nr. 162/2020 Neni 76(2)(c): a FLAT statutory de-minimis amount,
  in Albanian Lek (ALL) -- WebFetch-verified 2026-07-21 directly
  against the Public Procurement Agency's own official English
  translation ('PP Law 162-2020 English', app.gov.al legislation page).
  Unlike Bulgaria's ЗОП Art. 54(5) (1% of turnover, capped at BGN
  50,000), this carve-out does not scale with the operator's turnover
  at all -- it is the same fixed amount for every economic operator."
  10000.0)

(defn tax-arrears-exceeds-de-minimis?
  "Does `engagement`'s own declared `:tax-arrears-amount` (ALL) exceed
  the flat Neni 76(2)(c) de-minimis threshold? Missing/zero arrears
  never exceed it."
  [{:keys [tax-arrears-amount]}]
  (> (double (or tax-arrears-amount 0)) tax-arrears-de-minimis-threshold-all))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
