package io.github.flowboat.flowweather.api.hurricane

import android.content.Context
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.util.nextJsonObject
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.newSingleThreadContext
import me.xdrop.fuzzywuzzy.FuzzySearch
import me.xdrop.fuzzywuzzy.model.ExtractedResult
import timber.log.Timber

class OwmCityCompletor(private val context: Context) {
    private lateinit var db: Map<String, Int>
    private lateinit var dbReversed: Map<Int, String>
    private lateinit var dbIndexed: List<Pair<String, Int>>
    private lateinit var dbNamesOnly: List<String>
    private lateinit var splitDbNamesOnly: List<List<String>>
    private var dbChunkSize = 0

    private fun loadCompletor() {
        context.resources.openRawResource(R.raw.owm_city_list).bufferedReader().use {
            val reader = JsonReader(it)
            reader.beginArray()

            val newDb = mutableMapOf<String, Int>()
            val newDbReversed = mutableMapOf<Int, String>()
            val newDbIndexed = mutableListOf<Pair<String, Int>>()
            val newDbNamesOnly = mutableListOf<String>()

            while(reader.peek() == JsonToken.BEGIN_OBJECT) {
                val obj = reader.nextJsonObject()

                val key = obj["name"].string + ", " + obj["country"].string
                val value = obj["id"].int

                newDb[key] = value
                newDbReversed[value] = key
                newDbIndexed.add(key to value)
                newDbNamesOnly.add(key)
            }

            db = newDb
            dbReversed = newDbReversed
            dbIndexed = newDbIndexed
            dbNamesOnly = newDbNamesOnly
            // TODO Use total CPUs
            dbChunkSize = dbNamesOnly.size / 4
            splitDbNamesOnly = dbNamesOnly.chunked(dbChunkSize)
        }
    }

    init {
        loadCompletor()
    }

    fun complete(partialCity: String): List<Pair<String, Int>> {
        return FuzzySearch.extractTop(partialCity, dbNamesOnly, TAKE_LIMIT).map {
            dbIndexed[it.index]
        }
    }

    suspend fun concurrentComplete(partialCity: String): List<Pair<String, Int>> {
        val jobs = mutableListOf<Deferred<List<ExtractedResult>>>()

        splitDbNamesOnly.forEachIndexed { index, list ->
            jobs += async(newSingleThreadContext("HurricaneComplete $index")) {
                Timber.d("Starting complete job $index...")
                FuzzySearch.extractTop(partialCity, list, TAKE_LIMIT).map {
                    ExtractedResult(it.string, it.score, index * dbChunkSize + it.index)
                }
            }
        }

        return jobs.flatMap { it.await() }.sortedByDescending { it.score }.take(TAKE_LIMIT).map {
            dbIndexed[it.index]
        }
    }

    fun lookupCity(id: Int) = dbIndexed.find { it.second == id }?.first

    companion object {
        private const val TAKE_LIMIT = 50
    }
}