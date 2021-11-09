(ns simulant.tx-functions
  (:require [datomic.client.api :as d]))

(defn sim-join
  "Add proc to the sim, if any slots are still available"
  [db simid proc]
  (let [procid (:db/id proc)
        procs (d/q '[:find ?procid
                     :in $ ?simid
                     :where [?simid :sim/processes ?procid]]
                   db simid)
        proc-assertions-list-forms (mapv (fn [[k v]]
                                           [:db/add procid k v])
                                         (dissoc proc :db/id))]
    (when (and (< (count procs) (:sim/processCount (d/pull db '[:sim/processCount] simid)))
               (not (some (fn [[e]] (= e procid)) procs)))
      (into proc-assertions-list-forms
            [[:db/add simid :sim/processes procid]
             [:db/add procid :process/ordinal (count procs)]]))))

(defn deliver
  "Set an attribute's value iff not already set. Idempotent."
  [db e a v]
  (when (zero? (count (d/q '[:find ?e ?a
                             :in $ ?e ?a
                             :where [?e ?a]]
                           db e a)))
    [[:db/add e a v]]))
