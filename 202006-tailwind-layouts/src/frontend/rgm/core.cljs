(ns ^:figwheel-hooks rgm.core
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [reitit.core :as r]
   [rgm.routing :as routing]
   [rgm.state :as state]))

(def default-db {::routing/current-route nil})

(defn Nav [{:keys [router current-route]}]
  [:ul
   (for [route-name (r/route-names router)
         :let       [route (r/match-by-name router route-name)
                     text (-> route :data :link-text)]]
     [:li {:key route-name}
      (when (= route-name (-> current-route :data :name))
        "> ")
      ;; Create a normal links that user can click
      [:a {:href (routing/href route-name)} text]])])

(defn Layout [{:keys [router]}]
  (let [current-route @(subscribe [::routing/current-route])]
    [:<>
     [Nav {:router router :current-route current-route}]
     (when current-route
       [(-> current-route :data :view)])]))

(defn ^:after-load mount-root
  []
  (re-frame/clear-subscription-cache!)
  (routing/init-routes!)
  (rdom/render [Layout {:router routing/router}]
               (.getElementById js/document "app")))

(defn ^:export init
  []
  (re-frame/dispatch-sync [:initialize-db default-db])
  (mount-root))

(comment
 (init)
 ;;; eval this to check if we're connected properly
 (js/alert "hi from editor via piggieback")
 (dispatch [::routing/navigate :route/sub-page2])
 )
