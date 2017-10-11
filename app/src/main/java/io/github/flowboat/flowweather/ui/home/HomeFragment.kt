package io.github.flowboat.flowweather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(HomePresenter::class)
class HomeFragment: BaseRxFragment<HomePresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_home, container, false)!!

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //TODO strings.xml
        setToolbarTitle("Home")
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}