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

package com.menucoo

import io.circe._
import io.circe.generic.semiauto._
import sttp.tapir.Schema

import com.menucoo.types._

/** A simple model for our hello world greetings.
  *
  * @param title
  *   A generic title.
  * @param headings
  *   Some header which might be presented prominently to the user.
  * @param message
  *   A message for the user.
  */
final case class Greetings(title: GreetingTitle, headings: GreetingHeader, message: GreetingMessage)

object Greetings {

  given Decoder[GreetingHeader] =
    Decoder.decodeString.emap(str => GreetingHeader.from(str).toRight("Invalid GreetingHeader!"))
  given Encoder[GreetingHeader] = Encoder.encodeString.contramap[GreetingHeader](_.toString)
  given Decoder[GreetingMessage] =
    Decoder.decodeString.emap(str => GreetingMessage.from(str).toRight("Invalid GreetingMessage!"))
  given Encoder[GreetingMessage] = Encoder.encodeString.contramap[GreetingMessage](_.toString)
  given Decoder[GreetingTitle] =
    Decoder.decodeString.emap(str => GreetingTitle.from(str).toRight("Invalid GreetingTitle!"))
  given Encoder[GreetingTitle] = Encoder.encodeString.contramap[GreetingTitle](_.toString)

  given Decoder[Greetings] = deriveDecoder[Greetings]
  given Encoder[Greetings] = deriveEncoder[Greetings]

  given Schema[Greetings] = Schema.derived[Greetings]

}
