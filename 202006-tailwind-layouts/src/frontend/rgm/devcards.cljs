(ns rgm.devcards
  (:require
   [devcards.core :refer [defcard-rg]]
   [reagent.core]))

(devcards.core/start-devcard-ui!)

(defcard-rg another-button
  "** some markdown documentation **"
  (fn [_data-atom _owner]
    [:button "hi"])
  {:some "initial data"}
  {:inspect-data true})
