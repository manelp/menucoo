package com.perezbondia.menucoo.api

import munit.CatsEffectSuite
import com.perezbondia.menucoo.core.model._

import JsonProtocol.given
import io.circe.syntax._
import io.circe.Json
import io.circe.parser.parse
import cats.effect.IO

class JsonProtocolTest extends CatsEffectSuite {

  test("weekMenu json encoding and decoding") {

    val dayMenu = DayMenu(Some(HomeMenu(List(Dish("Rice with chicken curry", Some("Delicious curry"))))), Some(OutMenu("can pizza")))

    val weekMenu = WeekMenu(dayMenu, dayMenu, dayMenu, dayMenu, dayMenu, dayMenu, dayMenu)
    val weekMenuString = """{
      |"monday": {"lunch": {"HomeMenu": {"dishes": [{"name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"tuesday": {"lunch": {"HomeMenu": {"dishes": [{"name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"wednesday": {"lunch": {"HomeMenu": {"dishes": [{"name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"thursday": {"lunch": {"HomeMenu": {"dishes": [{"name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"friday": {"lunch": {"HomeMenu": {"dishes": [{"name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"saturday": {"lunch": {"HomeMenu": {"dishes": [{"name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}},
      |"sunday": {"lunch": {"HomeMenu": {"dishes": [{"name":"Rice with chicken curry", "description": "Delicious curry"}]}}, "dinner":{"OutMenu":{"description": "can pizza"}}}
    }""".stripMargin

    val encodedJson = weekMenu.asJson
    val decodedJson = IO.fromEither(parse(weekMenuString))

    decodedJson.assertEquals(encodedJson)

  }

}
