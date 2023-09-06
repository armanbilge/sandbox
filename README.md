# feral println demo

Requires:
- [scala-cli](https://scala-cli.virtuslab.org/)
- [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html)

Compile the lambda into a JAR.
```
scala-cli --power package --assembly --preamble=false lambda.scala
```

Then start the API proxy.
```
sam local invoke
```

Then run a request.

```
$ curl http://127.0.0.1:3000/foo
bar
```
