package eu.oora.marvel.dependency.component

import dagger.Component
import eu.oora.marvel.MainApplication
import eu.oora.marvel.dependency.module.ApplicationModule
import eu.oora.marvel.dependency.scope.PerApplicationScope

@PerApplicationScope
@Component(
  modules = arrayOf(
    ApplicationModule::class
  )
)
interface ApplicationComponent {
  fun inject(application: MainApplication)
}
