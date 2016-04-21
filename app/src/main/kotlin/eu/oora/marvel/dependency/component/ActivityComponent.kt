package eu.oora.marvel.dependency.component

import dagger.Component
import eu.oora.marvel.MainActivity
import eu.oora.marvel.dependency.module.ActivityModule
import eu.oora.marvel.dependency.scope.PerActivityScope

@PerActivityScope
@Component(
  dependencies = arrayOf(
    ApplicationComponent::class
  ),
  modules = arrayOf(
    ActivityModule::class
  )
)
interface ActivityComponent : ApplicationComponent {
  fun inject(activity: MainActivity)
}
