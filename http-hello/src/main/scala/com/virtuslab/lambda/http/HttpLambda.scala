package com.virtuslab.lambda.http

import java.io.{InputStream, OutputStream, OutputStreamWriter}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization._
import HttpLambda.formats

case class Response(body: Option[String] = None,
                    statusCode: Int = 200,
                    headers: Map[String, Any] = Map.empty[String, Any])



object HttpLambda {
  implicit val formats = Serialization.formats(NoTypeHints)
}

class HttpLambda extends RequestStreamHandler {

  override def handleRequest(input: InputStream, output: OutputStream, context: Context): Unit = {
    context.getLogger.log(s"Log: ${context.getRemainingTimeInMillis}")
    println(s"Test ${context.getAwsRequestId}")
    
    try {
      val response = write(Response(Some(s"Fetched with ID: ${context.getAwsRequestId}")))
      context.getLogger.log(s"Generated response is: ${response}")

      val writer = new OutputStreamWriter(output, "UTF-8")
      writer.write(response)
      writer.flush()
      writer.close()
    } catch {
      case e: Throwable => context.getLogger.log(s"exception? -> ${e}")
    }
  }
}
