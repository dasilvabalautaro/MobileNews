package com.globalhiddenodds.mobilenews.workers

import com.globalhiddenodds.mobilenews.datasource.network.data.HitCloud

//Pattern Singleton
object WorkerUtils {

    @Suppress("UNCHECKED_CAST")
    fun getHits(items: MutableMap<String?, Any?>):
            ArrayList<Map<String?, Any?>> {
        var hits = arrayListOf<Map<String?, Any?>>()
        for (item in items){
            if (item.key == ConstantsHit.KEY_HITS){
                hits = item.value as ArrayList<Map<String?, Any?>>
                break
            }
        }

        return hits
    }

    fun setHits(list: ArrayList<Map<String?, Any?>>):
            MutableList<HitCloud> {
        val listHit = mutableListOf<HitCloud>()
        for (item in list){
            val stringMap: MutableMap<String, String> = mutableMapOf()
            val doubleMap: MutableMap<String, Double> = mutableMapOf()
            checkKeysString(item, stringMap)
            checkKeysDouble(item, doubleMap)
            val hitCloud = HitCloud(
                stringMap[ConstantsHit.KEY_OBJECT_ID]!!,
                doubleMap[ConstantsHit.KEY_PARENT_ID],
                doubleMap[ConstantsHit.KEY_STORY_ID],
                stringMap[ConstantsHit.KEY_TITLE],
                stringMap[ConstantsHit.KEY_AUTHOR],
                stringMap[ConstantsHit.KEY_STORY_URL],
                stringMap[ConstantsHit.KEY_CREATED],
                doubleMap[ConstantsHit.KEY_CREATED_I],
            )
            listHit.add(hitCloud)
        }

        return listHit
    }

    private fun checkKeysString(
        keysMap: Map<String?, Any?>,
        stringMap: MutableMap<String, String>) {
        val listKeys = listOf(
            ConstantsHit.KEY_OBJECT_ID,
            ConstantsHit.KEY_TITLE,
            ConstantsHit.KEY_AUTHOR,
            ConstantsHit.KEY_STORY_URL,
            ConstantsHit.KEY_CREATED)
        for (key in listKeys){
            if (keysMap[key] != null && keysMap[key] is String){
                val value = keysMap[key] as String
                stringMap[key] = value
            }
            else {
                stringMap[key] = ""
            }
        }
    }

    private fun checkKeysDouble(
        keysMap: Map<String?, Any?>,
        doubleMap: MutableMap<String, Double>) {
        val listKeys = listOf(
            ConstantsHit.KEY_PARENT_ID,
            ConstantsHit.KEY_STORY_ID,
            ConstantsHit.KEY_CREATED_I)
        for (key in listKeys){
            if (keysMap[key] != null && keysMap[key] is Double){
                val value = keysMap[key] as Double
                doubleMap[key] = value
            }
            else {
                doubleMap[key] = 0.0
            }
        }
    }
}