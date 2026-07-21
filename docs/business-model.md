# Business Model: Independent Public-Sector Market-Entry & Procurement Compliance Service — Republic of Albania

## Classification

- Repository: `cloud-itonami-iso3166-alb`
- ISO 3166: `ALB` (Albania)
- Activity: public-procurement market-entry and ongoing regulatory-
  compliance navigation for an already-incorporated operator
- Social impact: [:sme-market-access :public-spend-transparency :cross-border-friction-reduction]

## Customer

- an already-incorporated `cloud-itonami-cofog-{code}` /
  `cloud-itonami-isco-{code}` / `cloud-itonami-unspsc-{segment}` /
  `cloud-itonami-{ISIC}` operator wanting to bid on an Albanian public
  contract
- a foreign SME or civic-tech vendor entering the public sector in
  Albania for the first time
- a `cloud-itonami-M6910` client that has just completed incorporation
  and now needs public-sector market access

## Offer

- registration walkthrough for the Sistemi Elektronik i Prokurimit
  Publik (Electronic Procurement System, mandatory under Ligji
  Nr. 162/2020 "Për Prokurimin Publik" Neni 56), administered by the
  Agjencia e Prokurimit Publik (Public Procurement Agency, APP)
- business/tax registration checklist: Qendra Kombëtare e Biznesit
  (National Business Center, QKB) registration, which generates the
  NUIS (Numri Unik i Identifikimit të Subjektit); tax/social-security
  good-standing confirmation with the Drejtoria e Përgjithshme e
  Tatimeve (General Directorate of Taxation, DPT) where applicable
- dispute-resolution routing: complaints/appeals go to the Komisioni i
  Prokurimit Publik (Public Procurement Commission, KPP), whose
  decisions are administratively final short of the Administrative
  Court of Appeals
- ongoing regulatory-change monitoring subscription
- compliance-audit export package for the client's own records

## Revenue

- per-engagement market-entry fee (one-time registration + checklist
  completion)
- recurring regulatory-change monitoring subscription
- compliance-audit export package

## Trust Controls

- any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off (`:filing/submit` is never automated at any phase)
- a false or fabricated regulatory-requirement claim is a HARD hold that
  cannot be overridden by human approval alone -- it must be corrected
  against a cited official source first
- tax/social-security arrears above the Ligji Nr. 162/2020 Neni 76(2)(c)
  flat de-minimis threshold (ALL 10,000, a fixed statutory constant --
  NOT scaled to the operator's turnover) are a HARD hold on
  `:filing/submit` -- Neni 76(2) makes this a mandatory disqualification
  ground, so the governor independently recomputes against the fixed
  threshold rather than trusting a claimed figure
- this service does **not** provide legal or tax advice; characterization
  and filing on the client's behalf beyond checklist/draft assistance
  routes to Albanian-licensed counsel or a registered agent

## Boundary with adjacent actors (read before forking)

- **`cloud-itonami-M6910`**: helps a client BECOME a legal entity
  (incorporation, ISIC 6910) -- a prior, different regulatory phase
  (company law). This blueprint assumes incorporation is already done and
  handles public-procurement market entry (a different regulatory domain).
- **`cloud-itonami-cofog-{code}`**: a jurisdiction-agnostic operator
  template for ONE public function. This blueprint is the orthogonal
  jurisdiction-specific axis -- the two compose (fork a COFOG-function
  blueprint AND this one to operate in Albania).
