(ns rgm.views
  (:require
   [re-frame.core :as re-frame :refer [dispatch]]))

(defn HomePage []
  [:div
   [:h1 "This is home page"]
   [:button
    ;; Dispatch navigate event that triggers a (side)effect.
    {:on-click #(dispatch [:rgm.router/navigate :route/sub-page2])}
    "Go to sub-page 2"]])

(defn SubPage1 []
  [:div
   [:h1 "This is sub-page 1"]])

(defn SubPage2 []
  [:div
   [:h1 "This is sub-page 2"]])
