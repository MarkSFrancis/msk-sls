#!/bin/bash
# Builds then deploys
# Designed as a quick utility for developers - not for production pipelines

sbt helloHandler/assembly
sbt mskWriteHandler/assembly
# sbt mskReadHandler/assembly
npx cdk bootstrap
npx cdk deploy
