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

package com.perezbondia.menucoo

import cats.effect._
import cats.syntax.all._
import com.typesafe.config._
import com.perezbondia.menucoo.api._
import com.perezbondia.menucoo.config._
import com.perezbondia.menucoo.db.FlywayDatabaseMigrator
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.Router
import org.slf4j.LoggerFactory
import pureconfig._
import sttp.apispec.openapi.circe.yaml._
import sttp.tapir.docs.openapi._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.SwaggerUI

object Server extends IOApp {
  val log = LoggerFactory.getLogger(Server.getClass())

  override def run(args: List[String]): IO[ExitCode] = {
    val migrator = new FlywayDatabaseMigrator

    for {
      config <- IO(ConfigFactory.load(getClass().getClassLoader()))
      dbConfig <- IO(
        ConfigSource.fromConfig(config).at(DatabaseConfig.CONFIG_KEY.toString).loadOrThrow[DatabaseConfig]
      )
      serviceConfig <- IO(
        ConfigSource.fromConfig(config).at(ServiceConfig.CONFIG_KEY.toString).loadOrThrow[ServiceConfig]
      )
      _ <- migrator.migrate(dbConfig.url, dbConfig.user, dbConfig.pass)
      helloWorldRoutes = new HelloWorld[IO]
      docs             = OpenAPIDocsInterpreter().toOpenAPI(List(HelloWorld.greetings), "My Service", "1.0.0")
      swaggerRoutes    = Http4sServerInterpreter[IO]().toRoutes(SwaggerUI[IO](docs.toYaml))
      routes           = helloWorldRoutes.routes <+> swaggerRoutes
      httpApp          = Router("/" -> routes).orNotFound
      resource = EmberServerBuilder
        .default[IO]
        .withHost(serviceConfig.host)
        .withPort(serviceConfig.port)
        .withHttpApp(httpApp)
        .build
      fiber <- resource.use(server =>
        IO.delay(log.info("Server started at {}", server.address)) >> IO.never.as(ExitCode.Success)
      )
    } yield fiber
  }

}
