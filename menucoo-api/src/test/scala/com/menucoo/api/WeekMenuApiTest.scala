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

import java.util.UUID

import cats.effect.IO
import cats.effect.kernel.Ref
import cats.effect.kernel.Resource

import munit.CatsEffectSuite
import org.http4s.EntityDecoder
import org.http4s.HttpRoutes
import org.http4s.Method
import org.http4s.Request
import org.http4s.Status
import org.http4s.Uri
import org.http4s.circe._
import org.http4s.implicits._
import org.http4s.server.Router
import org.typelevel.ci.CIString

import com.menucoo.api.model.GenericError
import com.menucoo.core.model.shortString
import com.menucoo.test.TestHelpers._

class WeekMenuApiTest extends CatsEffectSuite {

  import JsonProtocol.given

  given EntityDecoder[IO, GenericError] = jsonOf

  test("GET /api/v1/menu/{reducedUUID} returns week menu") {
    val expectedStatusCode = Status.BadRequest

    val expectedContentType = "application/json"
    val expectedBody        = GenericError("not.implemented", "Not implemented")

    val response = testResources().use { service =>
      for {
        uuid <- IO(UUID.randomUUID.shortString)
        uri  <- Uri.fromString(s"/api/v1/menu/$uuid").toOption.getOrThrow
        request = Request[IO](method = Method.GET, uri = uri)
        response <- service.orNotFound.run(request)
      } yield response
    }

    val test = for {
      result      <- response
      body        <- result.as[GenericError]
      contentType <- result.headers.get(CIString("content-type")).getOrThrow
    } yield (result.status, body, contentType.head.value)
    test.assertEquals((expectedStatusCode, expectedBody, expectedContentType))

  }

  test("GET /api/v1/menu/{invalidUUID} returns 404 not found") {
    val expectedStatusCode = Status.NotFound

    val expectedContentType = "application/json"

    val response = testResources().use { service =>
      for {
        uri <- Uri.fromString(s"/api/v1/menu/1234").toOption.getOrThrow
        request = Request[IO](method = Method.GET, uri = uri)
        response <- service.orNotFound.run(request)
      } yield response
    }

    val test = for {
      result      <- response
      contentType <- result.headers.get(CIString("content-type")).getOrThrow
    } yield result.status
    test.assertEquals(expectedStatusCode)

  }

  def testResources(): Resource[IO, HttpRoutes[IO]] = Resource.eval(IO(Router("/" -> new WeekMenuApi[IO]().routes)))

}
