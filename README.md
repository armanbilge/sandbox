# feral-lambda-http4s demo

Requires:
- [scala-cli](https://scala-cli.virtuslab.org/)
- [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html)

Compile the lambda into a JS file.
```
scala-cli --power package lambda.scala
```

Then start the API proxy.
```
sam local start-api
```

Then run a request.

```
$ curl http://127.0.0.1:3000/foo
bar
```
