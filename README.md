# MSK SLS
Serverless MSK proof of concept, using scala as much as possible

# Architecture

HTTP endpoint -> API Gateway -> lambda -> MSK queue -> lambda -> dynamo-db

# Scope

## What's in scope?

- API Gateway
- Lambda backing for the API gateway
- Putting some data based on the API request into Kafka
- An async workflow based on a Lambda with a Kafka trigger, which places the data into dynamodb

## What's not in scope?

- Custom domain name for the API Gateway
- Connection to datadog

## Useful commands

 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!
