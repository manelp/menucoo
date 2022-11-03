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

package com.menucoo.api

import cats.effect._
import cats.syntax.all._

import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._
import sttp.model._
import sttp.tapir.CodecFormat.TextPlain
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir.server.http4s._

import com.menucoo.Greetings
import com.menucoo.api.types._
import com.menucoo.types._

final class HelloWorld[F[_]: Async] extends Http4sDsl[F] {
  final val message: GreetingMessage = GreetingMessage("This is a fancy message directly from http4s! :-)")

  implicit def decodeGreetings: EntityDecoder[F, Greetings] = jsonOf
  implicit def encodeGreetings: EntityEncoder[F, Greetings] = jsonEncoderOf

  private val sayHello: HttpRoutes[F] =
    Http4sServerInterpreter[F]().toRoutes(HelloWorld.greetings.serverLogic { name =>
      val greetings = (
        GreetingTitle.from(s"Hello $name!"),
        GreetingHeader.from(s"Hello $name, live long and prosper!")
      ).mapN { case (title, headings) =>
        Greetings(
          title = title,
          headings = headings,
          message = message
        )
      }
      Sync[F].delay(greetings.fold(StatusCode.BadRequest.asLeft[Greetings])(_.asRight[StatusCode]))
    })

  val routes: HttpRoutes[F] = sayHello

}

object HelloWorld {
  val example = Greetings(
    title = GreetingTitle("Hello Kirk!"),
    headings = GreetingHeader("Hello Kirk, live long and prosper!"),
    message = GreetingMessage("This is some demo message...")
  )

  val greetings: Endpoint[Unit, NameParameter, StatusCode, Greetings, Any] =
    endpoint.get
      .in("hello")
      .in(query[NameParameter]("name"))
      .errorOut(statusCode)
      .out(jsonBody[Greetings].description("A JSON object demo").example(example))
      .description(
        "Returns a simple JSON object using the provided query parameter 'name' which must not be empty."
      )
}
