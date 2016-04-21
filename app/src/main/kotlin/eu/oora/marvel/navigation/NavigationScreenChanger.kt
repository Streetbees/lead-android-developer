package eu.oora.marvel.navigation

import android.content.Context
import android.view.LayoutInflater
import eu.oora.marvel.view.MainLayout
import flow.Direction
import flow.KeyChanger
import flow.State
import flow.TraversalCallback
import timber.log.Timber

class NavigationScreenChanger(private val layout: MainLayout) : KeyChanger() {
  override fun changeKey(old: State?, new: State, direction: Direction, contexts: MutableMap<Any, Context>, callback: TraversalCallback) {
    val screen = new.getKey<Screen>()
    contexts[screen]?.let {
      Timber.d("Render screen %s", screen.name)

      // save old state
      old?.let { // take first (the only) view and save its bundle state
        it.save(layout.content.getChildAt(0))
      }

      render(it, screen, new, callback)
    }
  }

  private fun render(context: Context, screen: Screen, state: State, callback: TraversalCallback) {
    // inflate screen view
    val view = LayoutInflater.from(context).inflate(screen.layout, layout.content, false)

    // restore view state
    state.restore(view)

    // remove old views
    layout.content.removeAllViews()

    // add inflated screen to layout
    layout.content.addView(view)

    // callback to mark transaction successful
    callback.onTraversalCompleted()
  }
}
