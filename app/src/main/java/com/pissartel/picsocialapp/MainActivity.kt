package com.pissartel.picsocialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.pissartel.designsystem.theme.PicSocialAppTheme
import com.pissartel.domain.usecase.GetProfilePictureUseCase
import com.pissartel.picsocialapp.navigation.tabs.FeedTab
import com.pissartel.picsocialapp.navigation.tabs.MapTab
import com.pissartel.picsocialapp.navigation.tabs.ProfileTab
import com.pissartel.picsocialapp.navigation.tabs.ProfileTabNavigationItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getProfilePictureUseCase: GetProfilePictureUseCase

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            PicSocialAppTheme {
                TabNavigator(
                    MapTab,
                    tabDisposable = {
                        TabDisposable(
                            navigator = it,
                            tabs = listOf(MapTab, FeedTab, ProfileTab)
                        )
                    }
                ) { tabNavigator ->
                    Scaffold(
                        topBar = {
                            AnimatedVisibility(tabNavigator.current.options.title.isNotEmpty()) {
                                TopAppBar(
                                    title = { Text(text = tabNavigator.current.options.title) }
                                )
                            }
                        },
                        content = { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                CurrentTab()
                            }
                        },
                        bottomBar = {
                            NavigationBar {
                                TabNavigationItem(MapTab)
                                TabNavigationItem(FeedTab)
                                ProfileTabNavigationItem(getProfilePictureUseCase)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}
