;; ## Main tool's namespace
(ns tin-whistle-tabs.core
  (:use [tin-whistle-tabs.interface.cli :only (cli)])
  (:gen-class))


;; ## Program's entry point

(defn -main
  "Starts a user interface of a tool. If CLI arguments are supplied CLI version is started."
  [& args]
  (when-not (empty? args)
    (cli args)))