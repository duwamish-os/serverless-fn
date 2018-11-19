Pre-requisite
--------------

- [install aws-cli](https://docs.aws.amazon.com/cli/latest/userguide/cli-install-macos.html)

```
pip --version #pip install --upgrade pip -> 18.1
curl -O https://bootstrap.pypa.io/get-pip.py
python3 get-pip.py --user
pip3 install awscli --upgrade --user

pip3 list  | grep aws
awscli               1.16.56  

aws --version
```

- add aws user with enough policies
- [configure aws in local machine, or application](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html)

```
# ~/.aws/credentials

[default]                                                                                                                                              
aws_access_key_id = 
aws_secret_access_key = 
```

```
aws lambda list-functions --profile aws-federated --region us-west-2
```

build artifact
----------------

```
sbt assembly
```

deploy lambda handler
----------------------

create a [Identity Access Role](https://console.aws.amazon.com/iam/home#/roles) with a policy allowing access to Lambda server.
(to be quick, filter policies with lambda and select 14 policies). I will call it `root`, then copy the ARN

```
$ aws lambda delete-function --function-name inventory-event-processor --region us-west-2 --profile aws-default

$ aws lambda create-function --function-name inventory-event-processor --role arn:aws:iam::<<12>>:role/root --runtime java8 --handler event.handlers.InventoryEventHandler::handleRequest --zip-file fileb://target/scala-2.12/inventory-api-assembly-1.0.jar --memory-size 256 --region us-west-2 --profile aws-default --vpc-config vpc-a
{
    "TracingConfig": {
        "Mode": "PassThrough"
    }, 
    "CodeSha256": "pZ7sOWFUFup1vL2RgnX5OUz2BrtkUOE2l0BbUdF1Nrc=", 
    "FunctionName": "inventory-event-processor", 
    "CodeSize": 5281382, 
    "RevisionId": "7121b65a-ecff-4b17-81e7-443c7ce5883c", 
    "MemorySize": 512, 
    "FunctionArn": "arn:aws:lambda:us-west-2:xxx:function:inventory-event-processor", 
    "Version": "$LATEST", 
    "Role": "arn:aws:iam::xxx:role/root", 
    "Timeout": 3, 
    "LastModified": "2018-11-16T04:36:30.882+0000", 
    "Handler": "event.handlers.InventoryEventHandler::handleRequest", 
    "Runtime": "java8", 
    "Description": ""
}
```

```
$ aws lambda list-functions --profile aws-default --region us-west-2
{
    "Functions": [
        {
            "TracingConfig": {
                "Mode": "PassThrough"
            }, 
            "Version": "$LATEST", 
            "CodeSha256": "pZ7sOWFUFup1vL2RgnX5OUz2BrtkUOE2l0BbUdF1Nrc=", 
            "FunctionName": "inventory-event-processor", 
            "MemorySize": 512, 
            "RevisionId": "7121b65a-ecff-4b17-81e7-443c7ce5883c", 
            "CodeSize": 5281382, 
            "FunctionArn": "arn:aws:lambda:us-west-2:xxx:function:inventory-event-processor", 
            "Handler": "event.handlers.InventoryEventHandler::handleRequest", 
            "Role": "arn:aws:iam::xxx:role/root", 
            "Timeout": 3, 
            "LastModified": "2018-11-16T04:36:30.882+0000", 
            "Runtime": "java8", 
            "Description": ""
        }
    ]
}

aws lambda get-function --function-name inventory-event-processor --profile aws-default --region us-west-2
{
    "Code": {
        "RepositoryType": "S3", 
        "Location": "https://awslambda-us-west-2-tasks.s3.us-west-2.amazonaws.com/snapshots/<<12>>/inventory-event-processor-5750f05b-0ec9-4616-b078-8df19fe48fcd?versionId=ep_6gB6Jhe4Y75Z0BaPhkzwetncVjW4F&X-Amz-Security-Token=FQoGZXIvYXdzEE0aDJfzi2PmYd7%2FD5E4EiK3A0mLnnmqL7kURH%2Fcp8g%2FQJA6j%2FKGspEBHfeDAClAtccs8Qu4SIHc1xlw1j249T5AHJdua86pjbTsxlUG3Q%2BihRknaIPVHicdTJscIpSvnhK56N5a6nzgfXb0mN8t9A97DZWKShiKChIv34bUTiGuqDIy%2BNun4T7CUlt3%2B6UBt9XXz%2Bo%2B5XczvIpVS%2FZTYGXaQaG7AtIGUI3CEzui1%2FTykNpy%2BUOTX8in%2B3E15hn39c71O2o0JDu2LZZPin5w3EKGY3vQ66eSfNUy0OOhVL9treCL0o8g2j1SvOXpvWDf2WZrpVBD8efDfj32PmTB8xiAAP9nOoqxaB6v7wVDxn75%2BDOdBYxin91N8p%2BmAShMl4H08qII733wY5OfiTqepw2t%2BHaSHBhFtGj3A6VjwXsE65HT7cvk2AwDjZNK9tcxdRtAPdhANo2RdsS3pI5MQwBJT%2B9JQKuMTKtHvDFLaomrdrNvnWkOwBMcg9L0xWCGOdpx02hM3sNbdMhbmkF7CuBmJ7DgmgkcA5LlDeVBccaBhT4snai%2FIkaUUCCEN6t2yJ3OrTiAtzz5KJRiZ4W1JKGLC3AM8DZiPnEo%2B8DD3wU%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20181118T042642Z&X-Amz-SignedHeaders=host&X-Amz-Expires=600&X-Amz-Credential=ASIAXJ4Z5EHBWKKN2K5Y%2F20181118%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Signature=9bb89edb49d3801a0b7676c9a4610ff335b7b7ac52e6343ecb019c1c8f73616f"
    }, 
    "Configuration": {
        "TracingConfig": {
            "Mode": "PassThrough"
        }, 
        "Version": "$LATEST", 
        "CodeSha256": "RHx2bd773SVAo6QqgVst4Jra/+wp69DI42Sw/0E0Mfw=", 
        "FunctionName": "inventory-event-processor", 
        "VpcConfig": {
            "SubnetIds": [
                "subnet-a", 
                "subnet-b", 
                "subnet-c"
            ], 
            "VpcId": "vpc-a", 
            "SecurityGroupIds": [
                "sg-a", 
                "sg-b", 
                "sg-c"
            ]
        }, 
        "MemorySize": 256, 
        "RevisionId": "2d123d09-ca00-41cb-932b-7915f47f10e2", 
        "CodeSize": 30062460, 
        "FunctionArn": "arn:aws:lambda:us-west-2:<<12>>:function:inventory-event-processor", 
        "Handler": "event.handlers.InventoryEventHandler::handleRequest", 
        "Role": "arn:aws:iam::<<12>>:role/lambda-vpc-role", 
        "Timeout": 3, 
        "LastModified": "2018-11-18T04:26:39.752+0000", 
        "Runtime": "java8", 
        "Description": ""
    }
}

```

run the event handler directly
---------------------

```
aws lambda invoke --invocation-type RequestResponse --function-name inventory-event-processor --payload '{"reqId": "81e590da-c1d0-4e36-a818-647f64434faa", "item": "sku-1", "qty": 100}' --region us-west-2 --profile aws-default output.text
{
    "FunctionError": "Unhandled", 
    "ExecutedVersion": "$LATEST", 
    "StatusCode": 200
}


$ cat output.text 
{"item":"shirts","totalQty":110}
```

Metrics
--------

![](metrics.png)


run lambda fn behind Rest API
-----------------------------

create [API gateway with lambda fn as a backend](https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-create-api-as-simple-proxy-for-lambda.html).

```
aws apigateway get-rest-apis --profile aws-default --region us-west-2

```


create a api request, deploy it

InventoryRequest

```
{
  "type" : "object",
  "properties" : {
    "item" : {
      "type" : "string"
    },
    "qty" : {
      "type" : "number"
    }
  }
}

#set($inputRoot = $input.path('$'))
{
  "item" : "sku-1",
  "qty" : 1
}
```

make a request:

```
curl -v --request POST -H "content-type: application/json" -d '{"item": "sku-1", "qty": 100}' https://<<10>>.execute-api.us-west-2.amazonaws.com/dev
```

References
-----------

Also see: https://github.com/prayagupd/amz-servermore

http://docs.aws.amazon.com/lambda/latest/dg/get-started-create-function.html

https://blog.symphonia.io/learning-lambda-part-5-743d8a99db53

https://docs.aws.amazon.com/lambda/latest/dg/java-programming-model-req-resp.html

**REST API references:**

https://docs.aws.amazon.com/apigateway/latest/developerguide/stages.html
