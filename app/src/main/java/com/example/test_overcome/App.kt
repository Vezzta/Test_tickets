package com.example.test_overcome

import android.app.Application
import com.example.test_overcome.ui.navigation.BottomNavigationDestination
import com.example.test_overcome.ui.navigation.Screens
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    companion object{
        val destinations = listOf(
            BottomNavigationDestination(
                icon = R.drawable.ic_tickets_new,
                title = R.string.home_tickets_title,
                route = Screens.HOME_SCREEN
            ),
            BottomNavigationDestination(
                icon = R.drawable.ic_tickets_in_progress,
                title = R.string.progress_tickets_title,
                route = Screens.PROCESS_SCREEN
            ),
            BottomNavigationDestination(
                icon = R.drawable.ic_tickets_finished,
                title = R.string.finished_tickets_title,
                route = Screens.FINISHED_SCREEN
            ),
        )
    }
}