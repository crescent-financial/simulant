(defproject com.datomic/datomic-sim "0.1.3"
  :description "Simulation testing with Datomic"
  :dev-dependencies [[lein-marginalia "0.7.1"]]
  :plugins [[lein-marginalia "0.7.1"]]
  :dependencies [[org.clojure/clojure "1.5.0-beta1"]
                 [org.clojure/test.generative "0.3.0"]
                 [com.datomic/datomic-free "0.8.3646"
                  :exclusions [org.clojure/clojure]]])
