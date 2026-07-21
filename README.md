# cloud-itonami-iso3166-alb

Open ISO 3166 Blueprint for **ALB**: Republic of Albania --
**`:implemented`**.

This repository designs **and implements** a forkable OSS business for
an independent public-sector market-entry consultant: an already-
incorporated operator (e.g. a `cloud-itonami-cofog-{code}`,
`cloud-itonami-isco-{code}`, `cloud-itonami-unspsc-{segment}` or
`cloud-itonami-{ISIC}` blueprint fork) gets a Compliance Advisor +
independent **Market-Entry Compliance Governor** to navigate public-
procurement registration, local business/tax registration, and
regulatory-compliance rules in Albania, so the operator can win and
service a government contract without hiring a full in-house compliance
department.

## Official surface

- Procurement: the Sistemi Elektronik i Prokurimit Publik (Electronic
  Procurement System, `https://www.app.gov.al/`), administered by the
  Agjencia e Prokurimit Publik (Public Procurement Agency, APP) under
  Ligji Nr. 162/2020, datë 23.12.2020, "Për Prokurimin Publik" (Law
  No. 162/2020 "On Public Procurement"). Notice publication in the
  electronic system is mandatory (Neni 56); APP itself manages the
  e-procurement database (Neni 23 shkronja "f") and may exclude an
  economic operator from public contracts for 3 months to 3 years for
  specified violations (Neni 78) -- APP publishes its own "Excluded
  economic operators" list.
- Business registration + tax identity: the Qendra Kombëtare e
  Biznesit (National Business Center, QKB, `https://qkb.gov.al/`),
  established by Ligji Nr. 131/2015, generates the NUIS (Numri Unik i
  Identifikimit të Subjektit, also labelled NIPT) at the moment of
  registration. The tax authority (Drejtoria e Përgjithshme e
  Tatimeve / General Directorate of Taxation, DPT,
  `https://www.tatime.gov.al/`) is notified electronically AFTER QKB
  has already generated the identifier -- confirmed from DPT's own
  "Business lifecycle" page, which states in its own words that QKB
  generates the NUIS and then "electronically notifies the tax
  authorities ... for the registration of a new subject." DPT does not
  issue NUIS itself.
- Dispute resolution: the Komisioni i Prokurimit Publik (Public
  Procurement Commission, KPP, `https://kpp.al/`) is the independent
  body (5 members, appointed by Parliament) that reviews public-
  procurement complaints/appeals under Ligji Nr. 162/2020; its
  decisions are administratively final, appealable only to the
  Administrative Court of Appeals.

## Implementation (R0)

| Piece | Location |
|---|---|
| Actor namespaces | `src/marketentry/*` |
| Governor | `:market-entry-compliance-governor` |
| Ops | `:engagement/intake` · `:jurisdiction/assess` · `:filing/draft` · `:filing/submit` |
| Flagship HARD check | `tax-arrears-exceeds-de-minimis` (Ligji Nr. 162/2020 Neni 76(2)(c), a FLAT ALL 10,000 statutory constant independently recomputed -- see `docs/adr/0001-architecture.md`) |
| Compliance catalog | `src/statute/facts.cljc` -- Commercial Companies Law, Labour Code, Personal Data Protection Law |
| Tests | `clojure -M:dev:test` |
| Demo | `clojure -M:dev:run` |
| Architecture ADR | [`docs/adr/0001-architecture.md`](docs/adr/0001-architecture.md) |

`:filing/submit` is never in any phase's `:auto` set -- human sign-off
is structural, not a rollout milestone.

## No robotics premise -- digital/data service exemption

Market-entry and procurement-compliance navigation is a pure data/software
service with no physical-domain work (portal registration, document
checklists, regulatory-change monitoring) -- the same exemption class as
`cloud-itonami-6310` (HR SaaS replacement) and `cloud-itonami-gtin-*`.
`blueprint.edn` sets `:itonami.blueprint/robotics false` and
`:required-technologies` lists only real capabilities (`:identity`,
`:forms`, `:dmn`, `:bpmn`, `:audit-ledger`), no `:robotics`.

## Core Contract

```text
operator intake + prior filing history
        |
        v
Compliance Advisor -> Market-Entry Compliance Governor -> filing draft, or human sign-off
        |
        v
gated portal registration / filing submission + audit ledger
```

No automated proposal can submit a portal registration or filing the
governor refuses, suppress a compliance record, or claim a legal/tax
conclusion the governor has not cleared. `:filing/submit` is never in any
phase's `:auto` set -- it always requires human sign-off.

## What this is NOT

- **Not the government of Albania.** This blueprint is an independent
  operator the government contracts with or that bids into its
  procurement -- never the government itself, and never an official
  channel.
- **Not legal or tax advice.** Every regulatory claim must cite the
  official source and route final filings to Albanian-licensed counsel
  or a registered agent where the law requires licensed representation.

## Capability layer

Required capabilities (`blueprint.edn`):

- :identity
- :forms
- :dmn
- :bpmn
- :audit-ledger

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md).

## License

AGPL-3.0-or-later.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) -- national dishes, protected products, beverages,
crafts, festivals and heritage sites for Albania:

- `src/culture/facts.cljc` -- the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` -- DataScript schema.
- `data/culture-tx.edn` -- derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis -- never fabricate one.
