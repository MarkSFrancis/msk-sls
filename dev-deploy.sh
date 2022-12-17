#!/bin/bash
# Builds then deploys
# Designed as a quick utility for developers - not for production pipelines

sbt helloHandler/assembly
npx cdk deploy