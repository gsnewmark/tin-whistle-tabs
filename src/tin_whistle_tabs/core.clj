;; ## Main tool's namespace
(ns tin-whistle-tabs.core
  (:require [tin-whistle-tabs.transformation-api :as api]
            [tin-whistle-tabs.interface :as ui]))


;; ## Program's entry point

(defn -main
  "Starts a user interface of a tool. If CLI arguments are supplied CLI version is started."
  [& args]
  (when-not (empty? args)
    (ui/cli args (api/transform-notes args) api/no-such-note-error)))