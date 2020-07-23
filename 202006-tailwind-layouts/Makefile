all: repl

repl: resources/public/styles.css
	clojure -A:dev:frontend:test --main rebel-readline.main

resources/public/styles.css: node_modules postcss.config.js $(CSS_SOURCES)
	yarn run postcss -c postcss.config.js -o "$@" src/frontend/**/*.css

node_modules: package.json
	yarn install
	@touch node_modules

clean:
	rm -rf target out pom.xml
	rm -rf nashorn_code_cache .cljs_nashorn_repl .cljs_node_repl
	rm -rf dist/*.js dist/*.map dist/css
	rm -rf .nrepl-port
	find . -name .DS_Store -exec rm {} \;

clobber: clean
	rm -rf tmp node_modules .cpcache .rebel_readline_history

lint:
	clj-kondo --lint src

.PHONY: repl clean clobber css

CLJS_SOURCES=$(shell find src/frontend -name "*.cljs")
CSS_SOURCES=$(shell find src/frontend -name "*.css")
