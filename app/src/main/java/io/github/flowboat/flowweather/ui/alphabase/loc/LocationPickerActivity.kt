package io.github.flowboat.flowweather.ui.alphabase.loc

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.api.hurricane.Hurricane
import io.github.flowboat.flowweather.ui.base.activity.BaseRxActivity
import io.github.flowboat.flowweather.util.hide
import io.github.flowboat.flowweather.util.show
import kotlinx.android.synthetic.main.activity_loc.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newSingleThreadContext
import nucleus5.factory.RequiresPresenter
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

@RequiresPresenter(LocationPickerPresenter::class)
class LocationPickerActivity : BaseRxActivity<LocationPickerPresenter>() {
    private val hurricane: Hurricane by Kodein.global.lazy.instance()

    private var adapter = FlexibleAdapter<LocationItem>(emptyList())

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        setContentView(R.layout.activity_loc)

        setupToolbar(toolbar)

        setupCompletor()
    }

    var lastChange = 0L
    private val DEBOUNCE_FREQ = 500L

    private fun setupCompletor() {
        city_list.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        city_list.adapter = adapter

        city_selector.textChangedListener {
            afterTextChanged {
                lastChange = System.currentTimeMillis()

                delay(DEBOUNCE_FREQ)

                if(System.currentTimeMillis() - lastChange >= DEBOUNCE_FREQ) {
                    tryCompletion(it.toString())
                }
            }
        }
    }

    private var existingJob: Job? = null

    fun tryCompletion(query: String) {
        existingJob?.cancel()

        launch(UI) {
            adapter.updateDataSet(emptyList())
            city_progress.show()
        }

        existingJob = launch(newSingleThreadContext("Complete thread")) {
            val res = completeCities(query)

            if(isActive) {
                launch(UI) {
                    city_progress.hide()
                    adapter.updateDataSet(res)
                }
            }
        }
    }

    fun completeCities(query: String): List<LocationItem> {
        return hurricane.cityCompletor.complete(query).map {
            LocationItem(it.first, it.second, this)
        }
    }
}