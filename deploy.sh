#!/usr/bin/env bash

mvn clean package

mdb=reactive-mongo-db
cf s | grep $mdb || cf cs mlab sandbox $mdb
cf push