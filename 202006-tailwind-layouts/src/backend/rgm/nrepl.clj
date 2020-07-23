(ns rgm.nrepl
  (:require
    [cider.nrepl]
    [cider.piggieback]
    [clojure.java.io :as io]
    [integrant.core :as ig]
    [nrepl.server :as nrepl]
    [taoensso.timbre :as timbre]))

(defn make-nrepl-handler []
  (let [middleware-symbols (conj cider.nrepl/cider-middleware
                                 'cider.piggieback/wrap-cljs-repl)
        middleware-vars (map resolve middleware-symbols)]
  (apply nrepl/default-handler middleware-vars)))

(defmethod ig/init-key :rgm/nrepl
  [_ {:keys [port] :or {port 0}}] ;; nrepl picks randomly if 0
  (let [server (nrepl/start-server
                 :port port
                 :handler (make-nrepl-handler))
        port (:port server)]
    (timbre/info "starting nrepl server on port" port)
    (spit ".nrepl-port" port)
    server))

(defmethod ig/halt-key! :rgm/nrepl
  [_ server]
  (timbre/info "stopping nrepl server")
  (nrepl/stop-server server)
  (io/delete-file ".nrepl-port" true))

