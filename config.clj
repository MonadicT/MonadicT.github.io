[:site-title "MonadicT"
 :site-description ""
 :site-url "http://MonadicT.github.io"
 :in-dir "_resources/"
 :out-dir "."
 :default-template "default.clj"
 :encoding "UTF-8"
 :blog-as-index false
 :create-archives false
 :org-export-command "(progn (org-html-export-as-html nil nil nil t nil) (with-current-buffer \"*Org HTML Export*\" (princ (org-no-properties (buffer-string)))))"
 :atomic-build true
 :emacs "/usr/local/bin/emacs"
 :emacs-eval [;;'(add-to-list 'load-path "~/tools/emacs/ext/org-mode/lisp/")
              ;;'(add-to-list 'load-path "~/source/emacs/ext/org-mode/contrib/lisp/")
              '(package-initialize)
              '(add-to-list 'load-path "~/.emacs.d/elpa/org-20140310/")
              '(add-to-list 'load-path "~/.emacs.d")
              '(add-to-list 'load-path "~/.emacs.d/elpa")
              ;;'(add-to-list 'load-path "~/.emacs.d/elpa/htmlize-1.39")
              '(add-to-list 'load-path "~/.emacs.d/elpa/clojure-mode-2.1.1")
              '(require 'htmlize)
              '(require 'org)
              '(require 'org-list)
              ;;'(require 'org-macs)
              '(require 'ox-html)
              '(require 'ob)
              '(global-font-lock-mode 1)
              '(require 'clojure-mode)
              '(set-face-foreground 'font-lock-string-face "#afafff")
              '(set-face-foreground 'font-lock-keyword-face "#ff5f00")
              '(set-face-foreground 'font-lock-function-name-face "#d7af00")
              '(set-face-foreground 'font-lock-builtin-face "#afd700")
              '(set-face-foreground 'font-lock-comment-face "#008787")]]
