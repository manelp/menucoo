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

import cats.effect.kernel.Async
import cats.effect.kernel.Sync
import cats.implicits._

import org.http4s.HttpRoutes
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._
import sttp.model._
import sttp.model._
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.CodecFormat.TextPlain
import sttp.tapir._
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir.json.circe._
import sttp.tapir.server.http4s._

import com.menucoo.api.model._
import com.menucoo.core.model._

final class WeekMenuApi[F[_]: Async] extends Http4sDsl[F] {

  private val week: HttpRoutes[F] =
    Http4sServerInterpreter[F]().toRoutes(WeekMenuApi.weekMenu.serverLogic { menuId =>
      Sync[F].delay(GenericError("not.implemented", "Not implemented").asLeft[WeekMenu])
    })

  val routes: HttpRoutes[F] = week

}

object WeekMenuApi {

  import JsonProtocol.given

  val menuRoot: Endpoint[Unit, Unit, Unit, Unit, Any] =
    endpoint
      .in("api")
      .in("v1")
      .in("menu")

  val menuId: Endpoint[Unit, MenuId, Unit, Unit, Any] =
    menuRoot.in(path[MenuId]("menuId"))

  val weekMenu: Endpoint[Unit, MenuId, GenericError, WeekMenu, Any] =
    menuId
      .in("week")
      .get
      .errorOut(jsonBody[GenericError])
      .out(jsonBody[WeekMenu].description("The week menu json representation"))
      .description(
        "Returns a simple JSON object using the provided query parameter 'name' which must not be empty."
      )
}
