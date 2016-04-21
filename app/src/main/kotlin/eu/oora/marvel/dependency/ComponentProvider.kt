package eu.oora.marvel.dependency

import eu.oora.marvel.dependency.component.ApplicationComponent

interface ComponentProvider<T : ApplicationComponent> {
  val component: T
}