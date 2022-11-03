/*
 * Copyright (c) 2022 Manel Perez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.perezbondia.menucoo.api

import cats.effect._
import com.perezbondia.menucoo.Greetings
import com.perezbondia.menucoo.types._
import munit._
import org.http4s._
import org.http4s.circe._
import org.http4s.implicits._
import org.http4s.server.Router

class HelloWorldTest extends CatsEffectSuite {

  implicit def decodeGreetings: EntityDecoder[IO, Greetings] = jsonOf

  test("when parameter 'name' is missing") {
    val expectedStatusCode = Status.BadRequest

    Uri.fromString("/hello") match {
      case Left(_) =>
        fail("Could not generate valid URI!")
      case Right(u) =>
        def service: HttpRoutes[IO] = Router("/" -> new HelloWorld[IO].routes)
        val request = Request[IO](
          method = Method.GET,
          uri = u
        )
        val response = service.orNotFound.run(request)
        val test = for {
          result <- response
          body   <- result.as[String]
        } yield (result.status, body)
        test.assertEquals((expectedStatusCode, "Invalid value for: query parameter name (missing)"))
    }
  }

  test("when parameter 'name' is invalid") {
    val expectedStatusCode = Status.BadRequest

    Uri.fromString("/hello?name=") match {
      case Left(e) =>
        fail(s"Could not generate valid URI: $e")
      case Right(u) =>
        def service: HttpRoutes[IO] = Router("/" -> new HelloWorld[IO].routes)
        val request = Request[IO](
          method = Method.GET,
          uri = u
        )
        val response = service.orNotFound.run(request)
        val test = for {
          result <- response
          body   <- result.as[String]
        } yield (result.status, body)
        test.assertEquals(
          (
            expectedStatusCode,
            "Invalid value for: query parameter name"
          )
        )
    }
  }

  test("when parameter 'name' is valid") {
    val expectedStatusCode = Status.Ok

    val name = "Captain Kirk"
    val expectedGreetings = Greetings(
      title = GreetingTitle("Hello Captain Kirk!"),
      headings = GreetingHeader("Hello Captain Kirk, live long and prosper!"),
      message = GreetingMessage("This is a fancy message directly from http4s! :-)")
    )
    Uri.fromString(Uri.encode(s"/hello?name=$name")) match {
      case Left(e) =>
        fail(s"Could not generate valid URI: $e")
      case Right(u) =>
        def service: HttpRoutes[IO] = Router("/" -> new HelloWorld[IO].routes)
        val request = Request[IO](
          method = Method.GET,
          uri = u
        )
        val response = service.orNotFound.run(request)
        val test = for {
          result <- response
          body   <- result.as[Greetings]
        } yield (result.status, body)
        test.assertEquals((expectedStatusCode, expectedGreetings))
    }
  }
}
