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

package com.menucoo.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.util.Try

import sttp.tapir.Schema

opaque type YearWeekNumber = String
object YearWeekNumber {

  given Schema[YearWeekNumber] = Schema.string

  /** Create an instance of YearWeekNumber from the given String type.
    *
    * @param source
    *   An instance of type String which will be returned as a YearWeekNumber.
    * @return
    *   The appropriate instance of YearWeekNumber.
    */
  def apply(source: String): YearWeekNumber = source

  /** Try to create an instance of YearWeekNumber from the given String.
    *
    * @param source
    *   A String that should fulfil the requirements to be converted into a YearWeekNumber.
    * @return
    *   An option to the successfully converted YearWeekNumber.
    */
  def from(source: String): Option[YearWeekNumber] =
    Try(LocalDate.parse(s"$source-1", DateTimeFormatter.ISO_WEEK_DATE)).map(_ => YearWeekNumber(source)).toOption
}
