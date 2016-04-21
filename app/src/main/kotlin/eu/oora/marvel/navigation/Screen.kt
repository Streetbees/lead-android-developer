package eu.oora.marvel.navigation

import eu.oora.marvel.dependency.component.ActivityComponent

interface Screen {
  val layout: Int
  val name: String

  fun inject(parent: ActivityComponent): ActivityComponent
}