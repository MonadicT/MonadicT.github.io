(defun remove-unused-imports()
  (interactive)
  (save-excursion
    (let ((start (point-min))
          (end (point-max))
          imports
          unused
          last-import-line)
      (beginning-of-buffer)
      (while (< (point) end)
        (let ((ln (buffer-substring-no-properties (line-beginning-position) (line-end-position))))
          (if (string-match "import[\t ]*.*\\.\\([A-Za-z0-9]+\\)" ln)
              (progn
                (setq last-import-line (count-lines 1 (point)))
                (add-to-list
                 'imports
                 (list (match-string 1 ln) (line-beginning-position) (line-end-position)))))
          (forward-line 1)))
      (goto-line (+ last-import-line 2))
      (narrow-to-region
       (point)
       end)
      (mapcar
       (lambda (cls)
         (goto-char (point-min))
         (if (not (search-forward-regexp (car cls) (point-max) t))
             (add-to-list 'unused cls)))
       imports)
      (widen)
      (mapcar
       (lambda (cls)
         (let ((x (make-overlay (cadr cls) (caddr cls))))
           (overlay-put x 'face '(:background "grey80"))))
       unused)))
  (message "remove-unused-imports"))

(let ((s "import com.facebook.react.ReactPackage"))
  (string-match "import[\t ]*\\(.*\\)\\([A-Za-z0-9]+\\)" s)
  (match-string  2 s))

(global-set-key (kbd "C-c C-i") #'remove-unused-imports)
