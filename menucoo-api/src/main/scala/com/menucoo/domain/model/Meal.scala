package com.menucoo.domain.model

import java.util.UUID

final case class MealId(id: UUID) extends Product with Serializable

final case class Meal(mealId: MealId, name: String, description: Option[String])
