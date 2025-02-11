package com.ghostdev.explore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.ghostdev.explore.di.initKoin
import com.ghostdev.explore.navigation.NavGraph
import com.ghostdev.explore.ui.theme.ExploreTheme
import org.koin.core.context.GlobalContext.stopKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initKoin(
            this
        )
        setContent {
            ExploreApp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}

@Composable
private fun ExploreApp() {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }

    ExploreTheme(darkTheme = isDarkTheme) {
        Scaffold { innerPadding ->
            NavGraph(
                innerPadding = innerPadding,
                isDarkTheme = isDarkTheme,
                toggleTheme = { isDarkTheme = !isDarkTheme }
            )
        }
    }
}
