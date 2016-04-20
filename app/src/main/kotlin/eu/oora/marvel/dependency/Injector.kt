package eu.oora.marvel.dependency

interface Injector<T> {
  fun inject(target: T)
}