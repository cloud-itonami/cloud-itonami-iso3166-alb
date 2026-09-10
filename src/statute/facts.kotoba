(ns statute.facts
  "General-law compliance catalog for Albania (ALB) -- extends this
  repo's existing `marketentry.facts` (public-procurement market-entry
  only, narrow scope) with a second, orthogonal catalog of statutes a
  company operating in this jurisdiction must generally track for
  compliance. Mirrors cloud-itonami-iso3166-jpn/-deu/-bgr/-aze's
  `statute.facts` (ADR-2607141700, cloud-itonami-compliance-fact-
  federation).

  Every entry cites an OFFICIAL Albanian government-hosted URL --
  never fabricated (WebFetch-verified 2026-07-21; two of the three
  entries below were confirmed by directly downloading the PDF and
  extracting its text -- `pdftotext`, since this iteration's WebFetch
  tool could only report 'binary PDF, cannot render' for these
  specific documents -- and reading the actual law text, not a
  secondary summary):

  - Albania's official legal gazette/law database, qbz.gov.al (Qendra
    e Botimeve Zyrtare / Official Publishing Center, under the
    Ministry of Justice), DOES directly host readable law PDFs at a
    predictable path (confirmed working for Ligji Nr. 162/2020 'Për
    Prokurimin Publik' and for the Labour Code below) -- unlike
    Azerbaijan's e-qanun.az, qbz.gov.al's own search/'eli' landing
    pages are a JavaScript single-page app this iteration's tools could
    not render, but its underlying `/alfresco/webdav/...` document
    store IS directly fetchable once the exact law-number/date path is
    known. This iteration could not derive the exact qbz.gov.al path
    for the Civil Code (Law No. 7850/1994) within its time budget (several
    plausible date-based path guesses 404'd) -- rather than guess
    further, the Commerce/Companies-law and Civil-Code-adjacent entries
    below cite OTHER real, confirmed-working official sources instead
    (see each entry).
  - Albania does not have a Bulgaria/Germany-style single 'Commerce
    Act' inside its Civil Code the way Azerbaijan's Civil Code contains
    its own commercial-entity provisions -- Albania has a SEPARATE
    standalone commercial-companies law, Ligji Nr. 9901, datë
    14.4.2008, 'Për Tregtarët dhe Shoqëritë Tregtare' (On Entrepreneurs/
    Traders and Commercial Companies), confirmed genuinely distinct
    from the Civil Code (Law No. 7850/1994) via independent research
    rather than assumed by analogy to a sibling jurisdiction. Cited here
    from the tax authority's own official download (tatime.gov.al),
    which this iteration verified is a real, working, directly-
    downloadable PDF matching the exact law number/date/title.
  - Labour Code (Kodi i Punës): Ligji Nr. 7961, datë 12.7.1995, cited
    directly from qbz.gov.al's own document store -- confirmed by
    downloading the PDF and extracting its Albanian-language text,
    which opens with 'LIGJ Nr.7961, datë 12.7.1995 KODI I PUNËS I
    REPUBLIKËS SË SHQIPËRISË'.
  - Personal Data Protection: Ligji Nr. 124/2024, 'Për Mbrojtjen e të
    Dhënave Personale' (On Personal Data Protection), approved
    19.12.2024, in force from 31.01.2025, REPEALING the older Ligji
    Nr. 9887/2008 this iteration initially found via secondary sources
    -- catching this supersession (rather than citing the repealed
    2008 law) required specifically checking for a more recent
    replacement, the same 'verify currency, don't assume' discipline
    this loop's guardrails require. Cited from the Commissioner for the
    Right to Information and Protection of Personal Data's own site
    (idp.al), confirmed by downloading the PDF and extracting its
    English-language text, which opens with 'Law no. 124/2024 On
    Personal Data Protection'.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"ALB"
   [{:statute/id "alb.commercial-companies-law"
     :statute/title "Ligji Nr. 9901, datë 14.4.2008, 'Për Tregtarët dhe Shoqëritë Tregtare' (Law on Entrepreneurs and Commercial Companies)"
     :statute/jurisdiction "ALB"
     :statute/kind :law
     :statute/law-number "Ligj Nr. 9901, datë 14.4.2008"
     :statute/url "https://www.tatime.gov.al/eng/shkarko.php?id=59"
     :statute/url-provenance :official-tax-authority-hosted
     :statute/enacted-date "2008-04-14"
     :statute/retrieved-at "2026-07-21"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "alb.labour-code"
     :statute/title "Kodi i Punës i Republikës së Shqipërisë (Labour Code of the Republic of Albania)"
     :statute/jurisdiction "ALB"
     :statute/kind :law
     :statute/law-number "Ligj Nr. 7961, datë 12.7.1995"
     :statute/url "https://qbz.gov.al/alfresco/webdav/Aktet/ligj/kuvendi-i-shqiperise/1995/07/12/7961/base/ligj-1995-07-12-7961.pdf"
     :statute/url-provenance :official-gazette
     :statute/enacted-date "1995-07-12"
     :statute/retrieved-at "2026-07-21"
     :statute/topic #{:labor :employment}}
    {:statute/id "alb.personal-data-protection-law"
     :statute/title "Ligji Nr. 124/2024 'Për Mbrojtjen e të Dhënave Personale' (Law on Personal Data Protection)"
     :statute/jurisdiction "ALB"
     :statute/kind :law
     :statute/law-number "Ligj Nr. 124/2024, miratuar 19.12.2024, hyrë në fuqi 31.01.2025 (shfuqizon Ligjin Nr. 9887/2008)"
     :statute/url "https://idp.al/wp-content/uploads/2025/04/Law-no.124-2024-DP.pdf"
     :statute/url-provenance :official-data-protection-commissioner
     :statute/enacted-date "2025-01-31"
     :statute/retrieved-at "2026-07-21"
     :statute/topic #{:data-protection :privacy}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-alb statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "ALB")) " ALB statutes seeded with an "
                 "official government-hosted citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :data-protection)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
