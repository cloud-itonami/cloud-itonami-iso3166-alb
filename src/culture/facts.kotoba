(ns culture.facts
  "Country-level regional-culture catalog for Albania (ALB) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"ALB"
   [{:culture/id "alb.dish.tave-kosi"
     :culture/name "Tavë kosi"
     :culture/country "ALB"
     :culture/kind :dish
     :culture/summary "Traditional Albanian baked dish of mutton and rice covered with a mixture of yogurt, eggs, butter and flour, regarded as one of Albania's national dishes."
     :culture/url "https://en.wikipedia.org/wiki/Tav%C3%AB_kosi"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "alb.dish.byrek"
     :culture/name "Byrek"
     :culture/country "ALB"
     :culture/kind :dish
     :culture/summary "Savory filo pastry with fillings such as meat, cheese or spinach, called byrek in Albania and popular throughout the Balkans and the former Ottoman world."
     :culture/url "https://en.wikipedia.org/wiki/Byrek"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "alb.dish.flia"
     :culture/name "Flia"
     :culture/country "ALB"
     :culture/kind :dish
     :culture/summary "Multi-layered crêpe-like dish brushed with cream, typical of northern Albania and of the cuisine of Kosovo."
     :culture/url "https://en.wikipedia.org/wiki/Flia"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "alb.beverage.raki"
     :culture/name "Raki"
     :culture/name-local "Rakia"
     :culture/country "ALB"
     :culture/kind :beverage
     :culture/summary "Fruit brandy shared across Southeastern Europe; raki is a traditional drink in Albania."
     :culture/url "https://en.wikipedia.org/wiki/Rakia"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "alb.craft.qeleshe"
     :culture/name "Qeleshe"
     :culture/country "ALB"
     :culture/kind :craft
     :culture/summary "White brimless felt skull cap traditionally worn by Albanians, part of the Albanian national costume."
     :culture/url "https://en.wikipedia.org/wiki/Qeleshe"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "alb.festival.dita-e-veres"
     :culture/name "Dita e Verës"
     :culture/country "ALB"
     :culture/kind :festival
     :culture/summary "Albanian spring festival celebrated on March 14, marking the beginning of the spring-summer period with bonfires and traditional foods such as ballokume."
     :culture/url "https://en.wikipedia.org/wiki/Dita_e_Ver%C3%ABs"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "alb.heritage.butrint"
     :culture/name "Butrint"
     :culture/country "ALB"
     :culture/kind :heritage
     :culture/summary "Ancient city and archaeological site in Vlorë County, Albania, declared a UNESCO World Heritage Site in 1992."
     :culture/url "https://en.wikipedia.org/wiki/Butrint"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "alb.heritage.gjirokaster"
     :culture/name "Gjirokastër"
     :culture/country "ALB"
     :culture/kind :heritage
     :culture/summary "City in southern Albania noted for its Ottoman-era architecture, inscribed on the UNESCO World Heritage List in 2005 as part of the Historic Centres of Berat and Gjirokastra."
     :culture/url "https://en.wikipedia.org/wiki/Gjirokast%C3%ABr"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-alb culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "ALB"))
                 " ALB entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
