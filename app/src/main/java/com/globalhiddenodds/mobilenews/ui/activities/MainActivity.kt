package com.globalhiddenodds.mobilenews.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.globalhiddenodds.mobilenews.R
import com.globalhiddenodds.mobilenews.ui.configuration.AppScreens
import com.globalhiddenodds.mobilenews.ui.screens.HitsBody
import com.globalhiddenodds.mobilenews.ui.screens.WebBody
import com.globalhiddenodds.mobilenews.ui.theme.MobileNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileNewsApp()
        }
    }
}

@Composable
fun MobileNewsApp() {
    MobileNewsTheme {
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = AppScreens
            .fromRoute(backstackEntry.value?.destination?.route)
        val showBackButton = when (currentScreen.name) {
            AppScreens.Hits.name -> false
            else -> true
        }
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick =
                        {
                            navController.navigate(AppScreens.Hits.name) {
                                popUpTo(AppScreens.Web.name + "/{url}") { inclusive = true }
                            }
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                stringResource(R.string.lbl_back)
                            )
                        }
                    }
                }
            )
        }) {
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(it)
            )

        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Hits.name,
        modifier = modifier
    ) {
        composable(AppScreens.Hits.name) {
            HitsBody(navController)
        }
        composable(
            AppScreens.Web.name + "/{url}",
            arguments = listOf(navArgument("url")
            { type = NavType.StringType })
        ) {
            val urlRoute = it.arguments?.getString("url")
            WebBody(urlRoute)
        }
    }
}
