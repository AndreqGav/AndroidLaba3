package gavrolov.am.gifts.app.storages

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import gavrolov.am.gifts.app.helpers.DeviceStorage
import gavrolov.am.gifts.app.json.EventDeserializer
import gavrolov.am.gifts.app.model.Event
import gavrolov.am.gifts.app.model.eventTypes


const val FILE_NAME: String = "events.json"

class EventStorage {
    companion object {
        var data: MutableList<Event> = mutableListOf()

        fun save() {
            val gson = Gson()
            val json = gson.toJson(data)
            DeviceStorage.create(FILE_NAME, json)
        }

        fun load(): List<Event> {
            val deserializer = EventDeserializer("type")
            for (i in eventTypes.entries) {
                deserializer.registerBarnType(i.value, i.key)
            }

            val gson = GsonBuilder()
                .registerTypeAdapter(Event::class.java, deserializer)
                .create()
            val json = DeviceStorage.read(FILE_NAME)

            try {
                val token = object : TypeToken<List<Event>>() {}.type
                return gson.fromJson<List<Event>>(json, token) ?: mutableListOf()
            } catch (e: Exception) {
                println(e.message)
            }

            return listOf()
        }
    }
}