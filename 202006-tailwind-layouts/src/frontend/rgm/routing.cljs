(ns rgm.routing
  (:require
   [re-frame.core :as re-frame :refer [dispatch]]
   [reitit.coercion.spec :as rss]
   [reitit.frontend :as rf]
   [reitit.frontend.controllers :as rfc]
   [reitit.frontend.easy :as rfe]
   [rgm.views]))

(re-frame/reg-fx
 ::navigate!
 (fn [route]
   (apply rfe/push-state route)))

(re-frame/reg-event-fx
 ::navigate
 re-frame/debug
 (fn [_ [_ & route]]
   ;; See `navigate` effect in routes.cljs
   {::navigate! route}))

(re-frame/reg-event-db
 ::navigated
 re-frame/debug
 (fn [db [_ new-match]]
   (let [old-match   (::current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (prn old-match new-match)
     (assoc db ::current-route (assoc new-match :controllers controllers)))))

(re-frame/reg-sub
 ::current-route
 (fn [db]
   (::current-route db)))

(defn href
  "Return relative url for given route. Url can be used in HTML links."
  ([k]
   (href k nil nil))
  ([k params]
   (href k params nil))
  ([k params query]
   (rfe/href k params query)))

(def routes
  ["/"
   [""
    {:name      :route/home
     :view      rgm.views/HomePage
     :link-text "Home"
     :controllers
     [{;; Do whatever initialization needed for home page
       ;; I.e (re-frame/dispatch [::events/load-something-with-ajax])
       :start (fn [& params] (js/console.log "Entering home page" params))
       ;; Teardown can be done here.
       :stop  (fn [& params] (js/console.log "Leaving home page" params))}]}]
   ["sub-page1"
    {:name      :route/sub-page1
     :view      rgm.views/SubPage1
     :link-text "Sub page 1"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering sub-page 1" params))
       :stop  (fn [& params] (js/console.log "Leaving sub-page 1" params))}]}]
   ["sub-page2"
    {:name      :route/sub-page2
     :view      rgm.views/SubPage2
     :link-text "Sub-page 2"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering sub-page 2" params))
       :stop  (fn [& params] (js/console.log "Leaving sub-page 2" params))}]}]])

(defn on-navigate [new-match]
  (when new-match
    (dispatch [::navigated new-match])))

(def router
  (rf/router
   routes
   {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start! router
              on-navigate
              {:use-fragment false}))
