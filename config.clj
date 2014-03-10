[:site-title "MonadicT"
 :site-description ""
 :site-url "http://MonadicT.github.io"
 :in-dir "_resources/"
 :out-dir "_site"
 :default-template "default.clj"
 :encoding "UTF-8"
 :blog-as-index false
 :create-archives false
 :atomic-build true
 :emacs "/usr/bin/emacs"
 :emacs-eval ['(add-to-list 'load-path "~/source/emacs/ext/org-mode/lisp/")
              '(add-to-list 'load-path "~/source/emacs/ext/org-mode/contrib/lisp/")
              '(add-to-list 'load-path "~/.emacs.d/elpa/clojure-mode-2.1.1/")
              '(require 'htmlize)
              '(require 'org)
              '(require 'org-macs)
              '(require 'ox-html)
              '(require 'ob)
              '(global-font-lock-mode 1)
              '(require 'clojure-mode)
              '(set-face-foreground 'font-lock-string-face "#afafff")
              '(set-face-foreground 'font-lock-keyword-face "#ff5f00")
              '(set-face-foreground 'font-lock-function-name-face "#d7af00")
              '(set-face-foreground 'font-lock-builtin-face "#afd700")
              '(set-face-foreground 'font-lock-comment-face "#008787")]]
