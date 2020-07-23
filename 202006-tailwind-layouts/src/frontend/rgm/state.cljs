(ns rgm.state
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ [_ db]]
   db))

