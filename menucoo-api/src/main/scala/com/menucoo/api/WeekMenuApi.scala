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
import com.menucoo.domain.model._

final class WeekMenuApi[F[_]: Async] extends Http4sDsl[F] {

  private val menuGetRoute: HttpRoutes[F] =
    Http4sServerInterpreter[F]().toRoutes(WeekMenuApi.menuGet.serverLogic { menuId =>
      Sync[F].delay(GenericError("not.implemented", "Not implemented").asLeft[WeekMenu])
    })

  private val menuPutRoute: HttpRoutes[F] =
    Http4sServerInterpreter[F]().toRoutes(WeekMenuApi.menuPut.serverLogic { case (menuId, weekMenu) =>
      Sync[F].delay(GenericError("not.implemented", "Not implemented").asLeft[Unit])
    })

  val routes: HttpRoutes[F] = menuGetRoute <+> menuPutRoute

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

  val menuGet: Endpoint[Unit, MenuId, GenericError, WeekMenu, Any] =
    menuId.get
      .out(jsonBody[WeekMenu].description("The week menu json representation"))
      .errorOut(jsonBody[GenericError])
      .description("Return the week menu.")

  val menuPost: Endpoint[Unit, WeekMenu, GenericError, CreatedResponse, Any] =
    menuRoot.put
      .out(jsonBody[CreatedResponse])
      .in(jsonBody[WeekMenu])
      .errorOut(statusCode(StatusCode.BadRequest).and(jsonBody[GenericError]))
      .description("Update the week menu.")

  val menuPut: Endpoint[Unit, (MenuId, WeekMenu), GenericError, Unit, Any] =
    menuId.put
      .out(statusCode(StatusCode.Ok))
      .in(jsonBody[WeekMenu])
      .errorOut(statusCode(StatusCode.BadRequest).and(jsonBody[GenericError]))
      .description("Update the week menu.")

}
