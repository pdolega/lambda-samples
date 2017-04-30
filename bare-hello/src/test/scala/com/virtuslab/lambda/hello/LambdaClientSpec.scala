package com.virtuslab.lambda.hello

import com.amazonaws.services.lambda.invoke.LambdaFunction
import org.scalatest.{MustMatchers, WordSpec}
import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory

trait HelloLambdaClient {
  @LambdaFunction(functionName = "bare-lambda")
  def hello(): String
}

class LambdaClientSpec  extends WordSpec with MustMatchers {
  "Invoking lambda function" should {
    "work for sync mode" in {

      val helloLambda = LambdaInvokerFactory.builder
        .lambdaClient(AWSLambdaClientBuilder.defaultClient)
        .build(classOf[HelloLambdaClient])

      val returnValue = helloLambda.hello()

      returnValue must not be(null)
      returnValue must startWith("Fetched")
    }
  }
}
