CREATE TABLE meals (
  id UUID NOT NULL PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT
);

CREATE TABLE week_menus (
  id UUID NOT NULL PRIMARY KEY
);

CREATE TABLE day_menu {
  id UUID NOT NULLL PRIMARY KEY,
  
}

CREATE TABLE day_menu_meals (
  id UUID NOT NULL PRIMARY KEY,
  week_menu UUID NOT NULL REFERENCES week_menus(id),
  week_day TEXT NOT NULL,
  meal_time TEXT NOT NULL,
  meal_type TEXT NOT NULL,
  meal UUID REFERENCES meals(id),

  UNIQUE(week_menu, week_day, meal_time)
);