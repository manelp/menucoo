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

package com.menucoo.core

import java.nio.ByteBuffer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.UUID

import scala.util.Try

import cats.implicits._

import sttp.tapir.Schema

object model {
  final case class WeekMenu(
      monday: DayMenu,
      tuesday: DayMenu,
      wednesday: DayMenu,
      thursday: DayMenu,
      friday: DayMenu,
      saturday: DayMenu,
      sunday: DayMenu
  )

  final case class MenuId(id: UUID) extends Product with Serializable

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

  object UUIDHelper {

    extension (r: UUID) {
      private def uuidToBytes: Array[Byte] = {
        val bb = ByteBuffer.allocate(16)
        bb.putLong(r.getMostSignificantBits())
        bb.putLong(r.getLeastSignificantBits())
        bb.array()
      }
      def shortString: String = new String(Base64.getUrlEncoder().encode(uuidToBytes))
    }
    private def bytesToUUID(source: Array[Byte]) = {
      val bb           = ByteBuffer.wrap(source)
      val mostSigBits  = bb.getLong()
      val leastSigBits = bb.getLong()
      new UUID(mostSigBits, leastSigBits)
    }

    def from(source: String): Try[UUID] = Try(bytesToUUID(Base64.getUrlDecoder().decode(source.getBytes())))
  }

  sealed trait Menu

  final case class HomeMenu(meals: List[Meal]) extends Menu

  final case class OutMenu(description: String) extends Menu

  final case class Meal(name: String, description: Option[String])

  final case class DayMenu(lunch: Option[Menu], dinner: Option[Menu])

}
