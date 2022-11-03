/*
 * Copyright (c) 2019 Contributors as noted in the AUTHORS.md file
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.menucoo.db

import com.typesafe.config.ConfigFactory
import com.menucoo.config._
import com.menucoo.types._
import munit._
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import pureconfig._

import scala.util.Try

final class FlywayDatabaseMigratorTest extends CatsEffectSuite {

  private val dbConfig = ConfigSource
    .fromConfig(ConfigFactory.load(getClass().getClassLoader()))
    .at(DatabaseConfig.CONFIG_KEY.toString)
    .loadOrThrow[DatabaseConfig]

  private val db = new Fixture[Unit]("database") {
    def apply(): Unit = ()
    override def beforeAll(): Unit = {
      val _        = Class.forName(dbConfig.driver.toString)
      val database = dbConfig.url.toString.split("/").reverse.take(1).mkString
      val connection = java.sql.DriverManager
        .getConnection(
          dbConfig.url.toString.replace(database, "template1"),
          dbConfig.user.toString,
          dbConfig.pass.toString
        )
      val statement = connection.createStatement()
      Try {
        statement.execute(s"""DROP DATABASE IF EXISTS "$database"""")
        statement.execute(s"""CREATE DATABASE "$database"""")
      } match {
        case scala.util.Failure(e) =>
          println(e.getMessage)
        case scala.util.Success(_) => // NOP
      }
      statement.close()
      connection.close()
    }

    override def beforeEach(context: BeforeEach): Unit = {
      val flyway: Flyway =
        Flyway
          .configure()
          .dataSource(dbConfig.url.toString, dbConfig.user.toString, dbConfig.pass.toString)
          .cleanDisabled(false)
          .load()
      val _ = flyway.migrate()
      val _ = flyway.clean()
    }

    override def afterEach(context: AfterEach): Unit = {
      val flyway: Flyway =
        Flyway
          .configure()
          .dataSource(dbConfig.url.toString, dbConfig.user.toString, dbConfig.pass.toString)
          .cleanDisabled(false)
          .load()
      val _ = flyway.migrate()
      val _ = flyway.clean()
    }
  }

  override def munitFixtures = List(db)

  test("FlywayDatabaseMigrator must update available outdated database") {
    val migrator = new FlywayDatabaseMigrator
    val program  = migrator.migrate(dbConfig.url, dbConfig.user, dbConfig.pass)
    program.map(s => assert(s.migrationsExecuted > 0))
  }

  test("FlywayDatabaseMigrator must not update available up to date database") {
    val migrator = new FlywayDatabaseMigrator
    val program = for {
      _ <- migrator.migrate(dbConfig.url, dbConfig.user, dbConfig.pass)
      s <- migrator.migrate(dbConfig.url, dbConfig.user, dbConfig.pass)
    } yield s
    program.map(s => assertEquals(s.migrationsExecuted, 0))
  }

  test("FlywayDatabaseMigrator must throw an exception if database is not available") {
    val cfg = DatabaseConfig(
      driver = JdbcDriverName("this.is.no.driver.name"),
      url = JdbcUrl("jdbc:nodriver://some.host/whatever"),
      user = JdbcUsername("no-user"),
      pass = JdbcPassword("no-password")
    )
    val migrator = new FlywayDatabaseMigrator
    val program  = migrator.migrate(cfg.url, cfg.user, cfg.pass)
    intercept[FlywayException](program.unsafeRunSync())
  }

}
