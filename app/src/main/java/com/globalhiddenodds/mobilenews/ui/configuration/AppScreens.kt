package com.globalhiddenodds.mobilenews.ui.configuration

enum class AppScreens {
    Hits(),
    Web();

    companion object {
        fun fromRoute(route: String?): AppScreens =
            when (route?.substringBefore("/")) {
                Hits.name -> Hits
                Web.name -> Web
                null -> Hits
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}