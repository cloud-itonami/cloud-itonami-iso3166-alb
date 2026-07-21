(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of Albanian procurement law, whether a claimed engagement fee
  actually equals base + months x rate, whether a declared tax/social-
  security arrears amount actually clears the Neni 76(2)(c) flat
  de-minimis threshold, whether a NUIS (Numri Unik i Identifikimit të
  Subjektit) has been verified for a filing that requires it, or when a
  draft stops being a draft and becomes a real-world app.gov.al
  Electronic Procurement System submission, so this MUST be a separate
  system able to *reject* a proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints).

  This blueprint's own text (docs/business-model.md Trust Controls:
  'any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off'; 'a false or fabricated regulatory-requirement claim
  is a HARD hold') names exactly the checks below.

  Six checks, in priority order, ALL HARD violations: a human
  approver CANNOT override them. The confidence/actuation gate is
  SOFT: it asks a human to look (low confidence / actuation), and the
  human may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file?
    3. Tax arrears exceed
       de-minimis threshold          -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own declared
                                       `:tax-arrears-amount` exceeds the
                                       Ligji Nr. 162/2020 Neni 76(2)(c)
                                       flat de-minimis carve-out (ALL
                                       10,000, a fixed statutory
                                       constant, NOT a percentage of
                                       turnover) for the mandatory
                                       disqualification ground in
                                       Neni 76(2). FLAGSHIP genuinely
                                       new check for the iso3166 family
                                       (grep-verified absent as a
                                       governor check function name
                                       fleet-wide at build time) -- a
                                       flat-constant statutory-threshold
                                       recompute, a third distinct check
                                       SHAPE alongside Bulgaria's
                                       computed-formula threshold and
                                       Azerbaijan's boolean registry
                                       membership.
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. NUIS unverified              -- for `:filing/submit`, when the
                                       engagement declares
                                       `:requires-nuis? true`,
                                       INDEPENDENTLY check
                                       `:nuis-verified?`. CONDITIONAL on
                                       the engagement's own ground
                                       truth. Grounded in the NUIS
                                       (Numri Unik i Identifikimit të
                                       Subjektit) the National Business
                                       Center (QKB) generates on
                                       business registration.
    6. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real portal package and submitting a real portal
  registration are the two real-world actuation events this actor
  performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(NUIS/QKB登録/電子調達システム登録等)が充足していない状態での提案"}]))))

(defn- tax-arrears-exceeds-de-minimis-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own declared `:tax-arrears-amount` exceeds the Ligji
  Nr. 162/2020 Neni 76(2)(c) flat de-minimis threshold -- the flagship
  genuinely new check this vertical adds. This is a mandatory
  statutory disqualification ground (Neni 76(2)) once the de-minimis
  carve-out no longer applies, so unlike sibling actors' `:requires-X?`
  gated checks this one is evaluated for every `:filing/submit`, not
  conditionally."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (registry/tax-arrears-exceeds-de-minimis? e)
        [{:rule :tax-arrears-exceeds-de-minimis
          :detail (str subject " の未納税金・社会保険料延滞額(" (:tax-arrears-amount e)
                      " ALL)がLigji Nr. 162/2020 Neni 76(2)(c)のde minimis基準額("
                      registry/tax-arrears-de-minimis-threshold-all
                      " ALL)を超過 -- 第76条(2)項の義務的失格事由に該当 -- 提出提案は進められない")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- nuis-unverified-violations
  "For `:filing/submit`, when the engagement declares `:requires-nuis?
  true`, INDEPENDENTLY check `:nuis-verified?` -- CONDITIONAL on the
  engagement's own ground truth. Grounded in the NUIS (Numri Unik i
  Identifikimit të Subjektit) the National Business Center (QKB)
  generates on business registration."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-nuis? e))
                 (not (true? (:nuis-verified? e))))
        [{:rule :nuis-unverified
          :detail (str subject " はNUIS(Numri Unik i Identifikimit të Subjektit)確認を要するが未確認 -- 提出提案は進められない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (tax-arrears-exceeds-de-minimis-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (nuis-unverified-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
