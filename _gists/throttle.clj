;; Copyright (C) 2014 by Praki Prakash

(ns monadict.throttle
  (require
   [clojure.core.async :as async]))

(defmacro defn-throttled
  "Defines a function which will only be invoked after a specified
   interval (ms). Syntax is the same as defn but precede the argument
    vector with interval specified in milliseconds.

   Example:
   (defn-throttled request-service 1000
      [args]
      ...) "
  [nm & forms]
  (let [doc-str (when (string? (first forms)) (take 1 forms))
        forms (if (string? (first forms)) (rest forms) forms)
        [interval-ms args] ((juxt first second) forms)
        forms (rest forms)
        f (gensym)]
    `(let [token-ch# (clojure.core.async/chan)]
       (defn ~nm ~@doc-str [~@args]
         (let [~f (fn  ~@forms)]
           (async/<!!
            (async/go
             (async/<! token-ch#)
             (apply ~f ~args)))))
       (async/go
        (loop []
          (async/<! (async/timeout ~interval-ms))
          (async/>! token-ch# true)
          (recur))))))
