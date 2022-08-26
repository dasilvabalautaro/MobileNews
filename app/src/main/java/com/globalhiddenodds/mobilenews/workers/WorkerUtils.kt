package com.globalhiddenodds.mobilenews.workers

import com.globalhiddenodds.mobilenews.datasource.network.data.HitCloud

//Pattern Singleton
object WorkerUtils {
    @Suppress("UNCHECKED_CAST")
    fun getHits(items: MutableMap<String?, Any>):
            ArrayList<Map<String?, Any>> {
        var hits = arrayListOf<Map<String?, Any>>()
        for (item in items){
            if (item.key == ConstantsHit.KEY_HITS){
                hits = item.value as ArrayList<Map<String?, Any>>
                break
            }
        }

        return hits
    }

    fun setHits(list: ArrayList<Map<String?, Any>>):
            MutableList<HitCloud> {
        val listHit = mutableListOf<HitCloud>()
        list.forEach {
            val hitCloud = HitCloud(
                it[ConstantsHit.KEY_PARENT_ID] as Double,
                it[ConstantsHit.KEY_STORY_ID] as Double,
                it[ConstantsHit.KEY_TITLE] as String,
                it[ConstantsHit.KEY_AUTHOR] as String,
                it[ConstantsHit.KEY_STORY_URL] as String,
                it[ConstantsHit.KEY_CREATED] as Double
            )
            listHit.add(hitCloud)
        }
        return listHit
    }
}