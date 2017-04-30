package com.virtuslab.lambda.hello

import java.io.{InputStream, OutputStream, OutputStreamWriter}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import com.virtuslab.lambda.hello.HelloLambda.formats
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization._

object HelloLambda {
  implicit val formats = Serialization.formats(NoTypeHints)
}

class HelloLambda extends RequestStreamHandler {

  override def handleRequest(input: InputStream, output: OutputStream, context: Context): Unit = {
    context.getLogger.log(s"Log: ${context.getRemainingTimeInMillis}")
    println(s"Test ${context.getAwsRequestId}")

    try {
      val writer = new OutputStreamWriter(output, "UTF-8")
      writer.write(write(s"Fetched with ID: ${context.getAwsRequestId}"))
      writer.flush()
      writer.close()
    } catch {
      case e: Throwable => context.getLogger.log(s"exception? -> ${e.getCause}")
    }
  }
}
