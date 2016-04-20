package eu.oora.marvel.dependency

import eu.oora.marvel.dependency.component.ActivityComponent
import eu.oora.marvel.navigation.Screen
import eu.oora.marvel.navigation.ServicePool
import flow.Services.Binder
import flow.ServicesFactory

class DependencyService(private val provider: ComponentProvider<ActivityComponent>) : ServicesFactory() {
  override fun bindServices(services: Binder) {
    val key: Screen = services.getKey()

    // create inject component
    val component = key.init(provider.component)

    // bind inject component for views
    services.bind(ServicePool.INJECT_COMPONENT, component)
  }
}