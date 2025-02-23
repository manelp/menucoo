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

import scala.util.Try

import io.circe._
import io.circe.generic.semiauto._
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.DecodeResult
import sttp.tapir.{ Codec => TapirCodec }

import com.menucoo.api.model._
import com.menucoo.domain.model.UUIDSyntax.shortString
import com.menucoo.domain.model._

object JsonProtocol {

  given Codec[GenericError] = deriveCodec[GenericError]

  given Codec[UUID] =
    Codec.from(
      Decoder.decodeString.emapTry(s => UUIDSyntax.fromShortString(s)),
      (a: UUID) => Encoder.encodeString(a.shortString)
    )

  given Codec[DishId] =
    Codec.from(Decoder[UUID].emapTry(id => Try(DishId(id))), (a: DishId) => Encoder[UUID].apply(a.id))  
    
  given Codec[Dish] = deriveCodec[Dish]
  
  given Codec[WeekMenuId] =
    Codec.from(Decoder[UUID].emapTry(id => Try(WeekMenuId(id))), (a: WeekMenuId) => Encoder[UUID].apply(a.id))
  given Codec[MealId] =
    Codec.from(Decoder[UUID].emapTry(id => Try(MealId(id))), (a: MealId) => Encoder[UUID].apply(a.id))
  given Codec[Meal]     = deriveCodec[Meal]
  given Codec[HomeMenu] = deriveCodec[HomeMenu]
  given Codec[OutMenu]  = deriveCodec[OutMenu]
  given Codec[Menu]     = deriveCodec[Menu]
  given Codec[DayMenu]  = deriveCodec[DayMenu]
  given Codec[WeekMenu] = deriveCodec[WeekMenu]

  given Codec[CreatedResponse] = deriveCodec[CreatedResponse]

  given PlainCodec[WeekMenuId] =
    TapirCodec.string
      .mapDecode(s => DecodeResult.fromOption(UUIDSyntax.fromShortString(s).map(WeekMenuId.apply).toOption))(
        _.id.shortString
      )
  given PlainCodec[YearWeekNumber] =
    TapirCodec.string.mapDecode(x => DecodeResult.fromOption(YearWeekNumber.from(x)))(_.toString)

}
