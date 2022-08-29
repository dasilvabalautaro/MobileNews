package com.globalhiddenodds.mobilenews.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.work.WorkInfo
import com.globalhiddenodds.mobilenews.R
import com.globalhiddenodds.mobilenews.ui.composables.Detail
import com.globalhiddenodds.mobilenews.ui.composables.Title
import com.globalhiddenodds.mobilenews.ui.configuration.AppScreens
import com.globalhiddenodds.mobilenews.ui.configuration.Utils
import com.globalhiddenodds.mobilenews.ui.data.HitView
import com.globalhiddenodds.mobilenews.viewmodel.DownloadHitsViewModel
import com.globalhiddenodds.mobilenews.viewmodel.ToPersistViewModel
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HitsBody(
    navController: NavHostController,
    toPersistViewModel: ToPersistViewModel = hiltViewModel(),
    downloadHitsViewModel: DownloadHitsViewModel = hiltViewModel()
) {
    //ProgressBarHit(isDisplayed = true)
    val context = LocalContext.current

    if (Utils.isConnect(context)) {
        ObserverWorkInfo(
            downloadHitsViewModel = downloadHitsViewModel,
            toPersistViewModel = toPersistViewModel,
            navController
        )
        downloadHitsViewModel.downHits()

    } else {
        GetHitsView(toPersistViewModel, navController)
    }

}

@Composable
private fun GetHitsView(
    toPersistViewModel: ToPersistViewModel,
    navController: NavHostController
) {
    toPersistViewModel.getHits()
    ObserverListHit(toPersistViewModel, navController)
}

@Composable
private fun ObserverWorkInfo(
    downloadHitsViewModel: DownloadHitsViewModel,
    toPersistViewModel: ToPersistViewModel,
    navController: NavHostController
) {
    val list by downloadHitsViewModel.workInfo.observeAsState()
    list?.let {
        when (it[0].state) {
            WorkInfo.State.SUCCEEDED -> AddPersistence(
                toPersistViewModel = toPersistViewModel,
                navController
            )
            WorkInfo.State.FAILED -> MessageFailed()
            WorkInfo.State.RUNNING -> println("Running download")
            else -> {}
        }
    }
}

@Composable
private fun AddPersistence(
    toPersistViewModel: ToPersistViewModel,
    navController: NavHostController
) {
    toPersistViewModel.insert()
    ObserverTaskResult(
        toPersistViewModel = toPersistViewModel,
        navController
    )
}

@Composable
private fun ObserverTaskResult(
    toPersistViewModel: ToPersistViewModel,
    navController: NavHostController
) {
    val result by toPersistViewModel.taskResult.observeAsState()
    result?.let {
        if (it == stringResource(id = R.string.msg_success_insert) ||
            it == stringResource(id = R.string.msg_update)
        ) {
            GetHitsView(toPersistViewModel, navController)
        } else {
            println(it)
        }
    }
}

@Composable
private fun ObserverListHit(
    toPersistViewModel: ToPersistViewModel,
    navController: NavHostController
) {
    val list by toPersistViewModel.hitsView.observeAsState()
    list?.let {
        DrawScreen(hitsView = it, toPersistViewModel, navController)
    }
}

@Composable
private fun DrawScreen(
    hitsView: List<HitView>,
    toPersistViewModel: ToPersistViewModel,
    navController: NavHostController
) {
    LazyColumn(modifier = Modifier.padding(vertical = 1.dp)) {
        items(items = hitsView) { hit ->
            if (hit.author!!.isNotEmpty() &&
                hit.created!!.isNotEmpty() &&
                hit.storyTitle!!.isNotEmpty() &&
                hit.storyUrl!!.isNotEmpty()
            ) {
                HitCard(
                    hitView = hit,
                    toPersistViewModel, navController
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HitCard(
    hitView: HitView,
    toPersistViewModel: ToPersistViewModel,
    navController: NavHostController
) {
    val encodedUrl = URLEncoder
        .encode(hitView.storyUrl,
            StandardCharsets.UTF_8.toString())
    RevealSwipe(
        onBackgroundEndClick = { toPersistViewModel.updateState(hitView.objectId) },
        onContentClick = {
            navController
                .navigate(
                    AppScreens.Web.name + "/" + encodedUrl
                ) {
                    popUpTo(AppScreens.Hits.name) { inclusive = true }
                }
        },
        modifier = Modifier.padding(vertical = 5.dp),
        directions = setOf(RevealDirection.EndToStart),
        hiddenContentEnd = {
            Trash()
        },
        closeOnContentClick = true,
        backgroundCardEndColor = Color.Red
    ) {
        Card(
            shape = it
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Title(title = hitView.storyTitle!!)
                Detail(description = "${hitView.author!!} - ${hitView.lapse}")
            }
        }
    }
}

@Composable
private fun Trash() {
    Icon(
        modifier = Modifier.padding(horizontal = 25.dp),
        imageVector = Icons.Outlined.Delete,
        contentDescription = null,
        tint = Color.White
    )
}

@Composable
private fun MessageFailed() {
    println("Failed download")
}