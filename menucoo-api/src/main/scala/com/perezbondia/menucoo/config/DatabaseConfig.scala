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

package com.perezbondia.menucoo.config

import com.perezbondia.menucoo.types._
import pureconfig._

/** The configuration for a database connection.
  *
  * @param driver
  *   The class name of the JDBC driver.
  * @param url
  *   A JDBC URL.
  * @param user
  *   The username for the connection.
  * @param pass
  *   The password for the connection.
  */
final case class DatabaseConfig(
    driver: JdbcDriverName,
    url: JdbcUrl,
    user: JdbcUsername,
    pass: JdbcPassword
)

object DatabaseConfig {
  // The default configuration key to lookup the database configuration.
  final val CONFIG_KEY: ConfigKey = ConfigKey("database")

  given ConfigReader[JdbcDriverName] = ConfigReader.fromStringOpt(JdbcDriverName.from)
  given ConfigReader[JdbcPassword]   = ConfigReader.fromStringOpt(JdbcPassword.from)
  given ConfigReader[JdbcUrl]        = ConfigReader.fromStringOpt(JdbcUrl.from)
  given ConfigReader[JdbcUsername]   = ConfigReader.fromStringOpt(JdbcUsername.from)
  given ConfigReader[DatabaseConfig] = ConfigReader.forProduct4("driver", "url", "user", "pass")(DatabaseConfig.apply)

}
