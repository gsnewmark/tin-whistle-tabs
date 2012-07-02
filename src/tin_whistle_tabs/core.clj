;; ## Main tool's namespace
(ns tin-whistle-tabs.core
  (:use [tin-whistle-tabs.interface.cli :only (cli)]
        [tin-whistle-tabs.interface.gui :only (gui)])
  (:gen-class))


;; ## Program's entry point

(defn -main
  "Starts a user interface of a tool. If CLI arguments are supplied CLI version is started."
  [& args]
  (if-not (empty? args)
    (cli args)
    (gui)))