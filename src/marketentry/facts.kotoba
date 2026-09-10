(ns marketentry.facts
  "Per-jurisdiction public-procurement market-entry regulatory catalog
  -- the G2-style spec-basis table the Market-Entry Compliance Governor
  checks every `:jurisdiction/assess` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's requirements,
  or did it invent one?').

  Albania's real market-entry surface (WebFetch-verified 2026-07-21,
  see each entry's own citation -- content actually fetched and read,
  including via direct PDF text extraction of official government
  documents, not taken from a secondary summary):

  - The Agjencia e Prokurimit Publik (Public Procurement Agency, APP,
    app.gov.al) administers the Sistemi Elektronik i Prokurimit Publik
    (Electronic Procurement System) under Ligji Nr. 162/2020, datë
    23.12.2020, 'Për Prokurimin Publik' (Law No. 162/2020 'On Public
    Procurement'). Neni 4 defines the 'Electronic procurement system'
    as 'a state database ... administered by the Public Procurement
    Agency'; Neni 23 letter 'f' lists 'manages the database of
    procurement procedures in the electronic procurement system' among
    APP's own competencies; Neni 56 mandates that procurement notices
    'shall be published ... in the electronic procurement system'. All
    three articles were read directly from APP's own official English
    translation ('PP Law 162-2020 English', downloaded from
    app.gov.al's legislation page), not a secondary source.
  - Business/tax identity: the Qendra Kombëtare e Biznesit (National
    Business Center, QKB, qkb.gov.al) is the registering authority --
    established by Ligji Nr. 131/2015, datë 26.11.2015, 'Për Qendrën
    Kombëtare të Biznesit' (merging the former National Registration
    Center and National Licensing Center). This iteration specifically
    investigated, rather than assumed, the QKB-vs-tax-authority
    question the task asked about: the tax authority's OWN site
    (tatime.gov.al, Drejtoria e Përgjithshme e Tatimeve / General
    Directorate of Taxation, DPT) states in its own words that 'the
    subjects registered based on the Law no. 9723 [dated 3.5.2007, On
    the Registration of Business] are provided with the unique
    identification number (NUIS/TIN), which is electronically
    generated' at QKB, and that 'the NBC [QKB], once the application is
    approved, electronically notifies the tax authorities ... for the
    registration of a new subject for tax purposes'. In other words:
    QKB generates the NUIS (Numri Unik i Identifikimit të Subjektit,
    also labelled NIPT on invoices) AT THE MOMENT of business
    registration; DPT's own role begins AFTER that, receiving an
    automatic electronic notification for tax-administration purposes
    -- DPT does not issue the identifier itself. This catalog cites QKB
    as `:corporate-number-owner-authority`, not DPT, on the strength of
    the tax authority's own page saying so.
  - `rep-spec-basis`: Neni 76(1) of Ligji Nr. 162/2020 mandates
    disqualification for economic operators convicted of specified
    serious criminal offenses (organized crime, corruption, fraud,
    money laundering/terrorist financing, forgery, child labor/
    trafficking), and its closing sentence extends this personal
    exclusion ground to 'the person convicted by a final court
    decision [who] is a member of its governing, management or
    supervisory body, shareholder or partner ... or has representative,
    decision-making or controlling powers within it'. This is real and
    narrow -- like Bulgaria's ЗОП Art. 54(2)-(3), it is a personal-
    exclusion-grounds extension to representatives, NOT a claim that
    Albania mandates a resident/domestic representative for public-
    tender participation itself. This iteration separately checked
    APP's own 'Register as a foreign operator' intake page
    (app.gov.al/register-operator/) for a broader domestic-
    representative mandate and found only a direct foreign-operator
    registration window, no stated local-representative requirement --
    so no stronger claim is made here.
  - Deliberately NOT modeled as a governor check in this catalog (see
    `marketentry.registry`'s own docstring): APP's Neni 78 power to
    exclude an economic operator from public contracts for 3 months to
    3 years (misinformation, contractual default, repeated contract-
    signing withdrawal, bid-rigging), and APP's own published
    'Excluded economic operators' list (app.gov.al/other/excluded-
    economic-operators/) that implements it. Both are real and
    WebFetch-confirmed to exist, but this iteration's flagship check is
    the Neni 76(2)(c) tax-arrears de-minimis threshold instead (see
    `marketentry.registry`) -- adding a second, boolean, registry-
    membership check on top would have been reaching for scope rather
    than depth; the Neni 78 list is left as a genuine, verified,
    not-yet-implemented extension point for a future iteration.

  Coverage is reported HONESTLY (see `coverage`): a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  intake/portal-registration/filing evidence set; `:legal-basis` /
  `:owner-authority` / `:provenance` are the G2 citation the governor
  requires before any `:jurisdiction/assess` proposal can commit.
  `:rep-owner-authority` / `:rep-legal-basis` / `:rep-provenance` are the
  SEPARATE representative-related citation `facts/rep-spec-basis`
  exposes -- for ALB this is honestly scoped to what Neni 76(1) of
  Ligji Nr. 162/2020 actually says (personal exclusion grounds for
  specified criminal convictions extend to representative/decision-
  making/controlling persons within the operator), NOT a claim that
  Albania mandates a resident/domestic representative the way some
  other jurisdictions in this catalog do."
  {"ALB" {:name "Albania"
          :owner-authority "Agjencia e Prokurimit Publik (Public Procurement Agency, APP) / Sistemi Elektronik i Prokurimit Publik (Electronic Procurement System, administered by APP)"
          :legal-basis "Ligji Nr. 162/2020, datë 23.12.2020, 'Për Prokurimin Publik' (Law No. 162/2020 'On Public Procurement') Neni 4 (Electronic procurement system, definition) + Neni 23 shkronja 'f' (APP competency: manages the database of procurement procedures in the electronic procurement system) + Neni 56 (mandatory notice publication in the electronic procurement system)"
          :national-spec "app.gov.al Electronic Procurement System economic-operator registration and tender participation, per Ligji Nr. 162/2020 Neni 56"
          :provenance "https://www.app.gov.al/"
          :required-evidence ["Ekstrakt i Qendrës Kombëtare të Biznesit (QKB) me Numrin Unik të Identifikimit të Subjektit (NUIS) (National Business Center extract with the Unique Subject Identification Number)"
                              "Regjistrim si operator ekonomik në Sistemin Elektronik të Prokurimit (app.gov.al Electronic Procurement System economic-operator registration record)"
                              "Vërtetim tatimor mbi shlyerjen e detyrimeve (tax/social-security good-standing record, Drejtoria e Përgjithshme e Tatimeve / DPT, where applicable)"
                              "Vërtetim përfaqësimi (authorized-representative confirmation record, Ligji Nr. 162/2020 Neni 76(1))"]
          :rep-owner-authority "Autoriteti/Enti Kontraktor (contracting authority/entity) / Agjencia e Prokurimit Publik (APP)"
          :rep-legal-basis "Ligji Nr. 162/2020 Neni 76(1) -- mandatory disqualification for conviction of specified criminal offenses (organized crime, corruption, fraud, money laundering/terrorist financing, forgery, child labor/trafficking) extends to natural persons who are members of the operator's governing/management/supervisory body, shareholders/partners, or hold representative, decision-making or controlling powers within it. NOT a standalone resident/domestic-representative mandate for procurement participation itself."
          :rep-provenance "https://www.app.gov.al/legislation/public-procurement/law/"
          :corporate-number-owner-authority "Qendra Kombëtare e Biznesit (National Business Center, QKB)"
          :corporate-number-legal-basis "NUIS (Numri Unik i Identifikimit të Subjektit, also labelled NIPT on invoices) -- electronically generated at the moment of business registration at QKB (Ligji Nr. 9723, datë 3.5.2007 'Për Regjistrimin e Biznesit', consolidated into QKB's remit by Ligji Nr. 131/2015, datë 26.11.2015 'Për Qendrën Kombëtare të Biznesit'). The tax authority (Drejtoria e Përgjithshme e Tatimeve / DPT) is notified electronically AFTER QKB has already generated the identifier -- DPT does not issue NUIS itself, per DPT's own 'Business lifecycle' page."
          :corporate-number-provenance "https://qkb.gov.al/en/home-3/"}
   "USA" {:name "United States"
          :owner-authority "U.S. General Services Administration (GSA) / SAM.gov"
          :legal-basis "Federal Acquisition Regulation (FAR); System for Award Management"
          :national-spec "SAM.gov entity registration + NAICS self-certification"
          :provenance "https://sam.gov/"
          :required-evidence ["EIN record"
                              "SAM.gov registration record"
                              "State business registration record"
                              "Authorized-representative record"]}
   "DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "Gesetz gegen Wettbewerbsbeschränkungen (GWB) / VgV"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to assess or file
  on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-alb R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative-related requirement map, or nil when
  this catalog has no such regime. For ALB this is real but intentionally
  narrow -- see the `catalog` docstring."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                       :corporate-number-legal-basis
                       :corporate-number-provenance]))))
