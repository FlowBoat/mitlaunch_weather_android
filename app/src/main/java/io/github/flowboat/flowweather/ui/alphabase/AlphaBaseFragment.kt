package io.github.flowboat.flowweather.ui.alphabase

import android.os.Bundle
import android.view.*
import com.github.matteobattilana.weather.PrecipType
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.alphabase.horiz.HorizAdapter
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import io.github.flowboat.flowweather.ui.bridgeui.BridgeUIPresenter
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_alphabase.*
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(BridgeUIPresenter::class)
class AlphaBaseFragment : BaseRxFragment<AlphaBasePresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_alphabase, container, false)!!

    val jumpSubject = PublishSubject.create<Int>()

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        setToolbarTitle("AlphaBase Test Viewer")

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherView.setWeatherData(PrecipType.RAIN)

        pager.adapter = HorizAdapter(jumpSubject)

        //Scroll master pager to center
        pager.setCurrentItem(0, false)
    }

    fun jumpToToday() {
        //Scroll master pager to center
        pager.setCurrentItem(0, true)
        //Notify adapter items to jump
        jumpSubject.onNext(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_alphabase, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.option_jump_today -> jumpToToday()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        parallaxImageView.registerSensorManager()
    }

    override fun onStop() {
        super.onStop()
        parallaxImageView.unregisterSensorManager()
    }

    companion object {
        fun newInstance() = AlphaBaseFragment()
    }
}
