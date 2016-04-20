package eu.oora.marvel.view.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import eu.oora.marvel.dependency.Injector
import eu.oora.marvel.navigation.ServicePool
import eu.oora.marvel.presenter.presenter.SplashScreenPresenter
import eu.oora.marvel.view.holder.SplashScreenViewHolder
import flow.Flow
import javax.inject.Inject

class SplashScreenView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs), SplashScreenViewHolder {
  @Inject
  lateinit var presenter: SplashScreenPresenter

  init {
    val component: Injector<SplashScreenView>? = Flow.getService(ServicePool.INJECT_COMPONENT, context)
    component?.inject(this)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    presenter.onTakeView(this)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    presenter.onDropView()
  }
}