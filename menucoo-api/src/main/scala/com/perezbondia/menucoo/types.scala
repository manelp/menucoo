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

import sttp.tapir.Schema

import scala.util.matching.Regex

object types {

  opaque type ConfigKey = String
  object ConfigKey {

    /** Create an instance of ConfigKey from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a ConfigKey.
      * @return
      *   The appropriate instance of ConfigKey.
      */
    def apply(source: String): ConfigKey = source

    /** Try to create an instance of ConfigKey from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a ConfigKey.
      * @return
      *   An option to the successfully converted ConfigKey.
      */
    def from(source: String): Option[ConfigKey] = Option(source).map(_.trim).filter(_.nonEmpty)

  }

  opaque type GreetingHeader = String
  object GreetingHeader {

    given Schema[GreetingHeader] = Schema.string

    /** Create an instance of GreetingHeader from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a GreetingHeader.
      * @return
      *   The appropriate instance of GreetingHeader.
      */
    def apply(source: String): GreetingHeader = source

    /** Try to create an instance of GreetingHeader from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a GreetingHeader.
      * @return
      *   An option to the successfully converted GreetingHeader.
      */
    def from(source: String): Option[GreetingHeader] = Option(source).filter(_.nonEmpty)

  }

  opaque type GreetingMessage = String
  object GreetingMessage {

    given Schema[GreetingMessage] = Schema.string

    /** Create an instance of GreetingMessage from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a GreetingMessage.
      * @return
      *   The appropriate instance of GreetingMessage.
      */
    def apply(source: String): GreetingMessage = source

    /** Try to create an instance of GreetingMessage from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a GreetingMessage.
      * @return
      *   An option to the successfully converted GreetingMessage.
      */
    def from(source: String): Option[GreetingMessage] = Option(source).filter(_.nonEmpty)

  }

  opaque type GreetingTitle = String
  object GreetingTitle {

    given Schema[GreetingTitle] = Schema.string

    /** Create an instance of GreetingTitle from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a GreetingTitle.
      * @return
      *   The appropriate instance of GreetingTitle.
      */
    def apply(source: String): GreetingTitle = source

    /** Try to create an instance of GreetingTitle from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a GreetingTitle.
      * @return
      *   An option to the successfully converted GreetingTitle.
      */
    def from(source: String): Option[GreetingTitle] = Option(source).filter(_.nonEmpty)

  }

  opaque type JdbcDriverName = String
  object JdbcDriverName {
    val Format: Regex = "^\\w+\\.[\\w\\d\\.]+[\\w\\d]+$".r

    /** Create an instance of JdbcDriverName from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a JdbcDriverName.
      * @return
      *   The appropriate instance of JdbcDriverName.
      */
    def apply(source: String): JdbcDriverName = source

    /** Try to create an instance of JdbcDriverName from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a JdbcDriverName.
      * @return
      *   An option to the successfully converted JdbcDriverName.
      */
    def from(source: String): Option[JdbcDriverName] = Option(source).filter(string => Format.matches(string))

  }

  opaque type JdbcPassword = String
  object JdbcPassword {

    /** Create an instance of JdbcPassword from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a JdbcPassword.
      * @return
      *   The appropriate instance of JdbcPassword.
      */
    def apply(source: String): JdbcPassword = source

    /** Try to create an instance of JdbcPassword from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a JdbcPassword.
      * @return
      *   An option to the successfully converted JdbcPassword.
      */
    def from(source: String): Option[JdbcPassword] = Option(source).filter(_.nonEmpty)

  }

  opaque type JdbcUrl = String
  object JdbcUrl {
    val Format: Regex = "^jdbc:[a-zA-z0-9]+:.*".r

    /** Create an instance of JdbcUrl from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a JdbcUrl.
      * @return
      *   The appropriate instance of JdbcUrl.
      */
    def apply(source: String): JdbcUrl = source

    /** Try to create an instance of JdbcUrl from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a JdbcUrl.
      * @return
      *   An option to the successfully converted JdbcUrl.
      */
    def from(source: String): Option[JdbcUrl] = Option(source).filter(string => Format.matches(string))

  }

  opaque type JdbcUsername = String
  object JdbcUsername {

    /** Create an instance of JdbcUsername from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a JdbcUsername.
      * @return
      *   The appropriate instance of JdbcUsername.
      */
    def apply(source: String): JdbcUsername = source

    /** Try to create an instance of JdbcUsername from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a JdbcUsername.
      * @return
      *   An option to the successfully converted JdbcUsername.
      */
    def from(source: String): Option[JdbcUsername] = Option(source).filter(_.nonEmpty)

  }
}
