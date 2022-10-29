/*
 * Copyright (c) 2022 Manel Perz
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

package com.perezbondia.menucoo.api

import sttp.model._
import sttp.tapir._
import sttp.tapir.CodecFormat.TextPlain

object types {
  opaque type NameParameter = String
  object NameParameter {

    given Codec[String, NameParameter, TextPlain] =
      Codec.string.mapDecode(str =>
          NameParameter
            .from(str)
            .fold(
              sttp.tapir.DecodeResult.Error(str, new IllegalArgumentException("Invalid name parameter value!"))
            )(name => sttp.tapir.DecodeResult.Value(name))
          )(_.toString)

    /** Create an instance of NameParameter from the given String type.
      *
      * @param source
      *   An instance of type String which will be returned as a NameParameter.
      * @return
      *   The appropriate instance of NameParameter.
      */
    def apply(source: String): NameParameter = source

    /** Try to create an instance of NameParameter from the given String.
      *
      * @param source
      *   A String that should fulfil the requirements to be converted into a NameParameter.
      * @return
      *   An option to the successfully converted NameParameter.
      */
    def from(source: String): Option[NameParameter] = Option(source).filter(_.nonEmpty)
  }
}
