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
import cats.effect.IO
import JsonProtocol.given
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import munit.CatsEffectSuite
import com.menucoo.domain.model.*
import com.menucoo.domain.model.UUIDSyntax.shortString

import java.time.{LocalTime, ZoneId}

class JsonProtocolTest extends CatsEffectSuite {

  test("weekMenu json encoding and decoding") {

    val menuId = WeekMenuId(UUID.randomUUID())
    val mealId = MealId(UUID.randomUUID())
    val dishId = DishId(UUID.randomUUID())
    val menuIdShort = menuId.id.shortString
    val mealIdShort = mealId.id.shortString
    val menu = HomeMenu(List())
    val since = LocalTime.now(ZoneId.of("UTC"))
    val until = LocalTime.now(ZoneId.of("UTC"))
    val dayMenu = DayMenu(
      List(Meal(mealId, "lunch", menu, since, until))
    )

    val weekMenu = WeekMenu(menuId, dayMenu, dayMenu, dayMenu, dayMenu, dayMenu, dayMenu, dayMenu)
    val weekMenuString = s"""{
      |"id":"$menuIdShort",
      |"monday": {"lunch": {"HomeMenu": {"meals": [{"mealId": "$mealIdShort", "name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"tuesday": {"lunch": {"HomeMenu": {"meals": [{"mealId": "$mealIdShort", "name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"wednesday": {"lunch": {"HomeMenu": {"meals": [{"mealId": "$mealIdShort", "name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"thursday": {"lunch": {"HomeMenu": {"meals": [{"mealId": "$mealIdShort", "name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"friday": {"lunch": {"HomeMenu": {"meals": [{"mealId": "$mealIdShort", "name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"saturday": {"lunch": {"HomeMenu": {"meals": [{"mealId": "$mealIdShort", "name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"sunday": {"lunch": {"HomeMenu": {"meals": [{"mealId": "$mealIdShort", "name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}}
    }""".stripMargin

    val encodedJson = weekMenu.asJson
    val decodedJson = IO.fromEither(parse(weekMenuString))

    decodedJson.assertEquals(encodedJson)

  }

  test("UUID shortening encoding and decoding") {
    for {
      uuid <- IO(UUID.randomUUID())
      encoded = uuid.asJson
      decoded <- IO.fromEither(encoded.as[UUID])
    } yield assertEquals(uuid, decoded)
  }

}
