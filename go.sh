#!/bin/sh

scala-cli --power package -f frontend.scala
scala-cli backend.scala
