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

package com.menucoo.config

import com.comcast.ip4s._
import pureconfig._

import com.menucoo.types._

/** The service configuration.
  *
  * @param host
  *   The hostname the service will listen on.
  * @param port
  *   The port number the service will listen on.
  */
final case class ServiceConfig(host: Host, port: Port)

object ServiceConfig {
  // The default configuration key to lookup the service configuration.
  final val CONFIG_KEY: ConfigKey = ConfigKey("service")

  given ConfigReader[Host]          = ConfigReader.fromStringOpt(Host.fromString)
  given ConfigReader[Port]          = ConfigReader.fromStringOpt(Port.fromString)
  given ConfigReader[ServiceConfig] = ConfigReader.forProduct2("host", "port")(ServiceConfig.apply)

}
