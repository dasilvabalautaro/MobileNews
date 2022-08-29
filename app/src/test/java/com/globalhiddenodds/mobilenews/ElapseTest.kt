package com.globalhiddenodds.mobilenews

import com.globalhiddenodds.mobilenews.ui.data.HitView
import com.globalhiddenodds.mobilenews.ui.data.elapse
import org.junit.Test

class ElapseTest {
    @Test
    fun elapse(){
        val hitView = HitView("32601160", "Password delisting",
            "stryan", "https://password.community",
            "2022-08-25T23:03:04.000Z")
        hitView.elapse()
        val result = hitView.lapse
        assert(result.isNotEmpty())
    }
}