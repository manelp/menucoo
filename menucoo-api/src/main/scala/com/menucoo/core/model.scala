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

  final case class MenuId(id: String) extends Product with Serializable

  sealed trait Menu

  final case class HomeMenu(dishes: List[Dish]) extends Menu

  final case class OutMenu(description: String) extends Menu

  final case class Dish(name: String, description: Option[String])

  final case class DayMenu(lunch: Option[Menu], dinner: Option[Menu])

}