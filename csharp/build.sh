#!/usr/bin/env bash
sh client/bin/generate.sh
(cd target/generated-sources && exec sh build.sh)
cp -f target/generated-sources/bin/* target/
(cd examples && exec sh build.sh)
