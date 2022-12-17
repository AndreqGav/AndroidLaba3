package gavrolov.am.gifts.app.json

import com.google.gson.*
import gavrolov.am.gifts.app.model.Event
import java.lang.reflect.Type


class EventDeserializer(private val animalTypeElementName: String) :
    JsonDeserializer<Event?> {
    private val gson: Gson = Gson()
    private val animalTypeRegistry: MutableMap<String, Class<out Event?>>

    init {
        animalTypeRegistry = HashMap()
    }

    fun registerBarnType(animalTypeName: String, animalType: Class<out Event?>) {
        animalTypeRegistry[animalTypeName] = animalType
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Event? {
        val animalObject: JsonObject = json.asJsonObject
        val animalTypeElement: JsonElement = animalObject.get(animalTypeElementName)
        val animalType: Class<out Event?> = animalTypeRegistry[animalTypeElement.asString]!!

        return gson.fromJson(animalObject, animalType)
    }
}