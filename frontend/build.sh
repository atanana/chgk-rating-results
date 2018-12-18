#!/usr/bin/env bash
rm -rf ../src/main/resources/web
parcel build index.html -d ../src/main/resources/web --public-url ./