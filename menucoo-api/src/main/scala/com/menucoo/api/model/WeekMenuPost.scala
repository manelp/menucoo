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

package com.menucoo.api.model

import com.menucoo.domain.model.Meal
import com.menucoo.domain.model.Menu
import java.time.LocalTime

sealed trait MenuRequest

final case class MealPost(name: String, menu: Menu, since: LocalTime, until: LocalTime)

final case class HomeMenuPost(meals: List[MealPost]) extends MenuRequest

final case class OutMenuPost(description: String) extends MenuRequest

final case class DayMenuPost(lunch: Option[MenuRequest], dinner: Option[MenuRequest])

final case class WeekMenuPost(
    monday: DayMenuPost,
    tuesday: DayMenuPost,
    wednesday: DayMenuPost,
    thursday: DayMenuPost,
    friday: DayMenuPost,
    saturday: DayMenuPost,
    sunday: DayMenuPost
)
