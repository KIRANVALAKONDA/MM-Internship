package com.example.studysnap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studysnap.R
import com.example.studysnap.model.Screen
import com.example.studysnap.navigation.SnapStudyNavHost
import com.example.studysnap.ui.components.SnapStudyBottomBar
import com.example.studysnap.ui.components.SnapStudyNavRail
import com.example.studysnap.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnapStudyApp(
    windowSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val useNavRail = windowSizeClass != WindowWidthSizeClass.Compact

    fun navigateTo(screen: Screen) {
        navController.navigate(screen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Row(modifier = Modifier.fillMaxSize()) {
        if (useNavRail) {
            SnapStudyNavRail(
                currentRoute = currentRoute,
                onNavigate = ::navigateTo
            )
        }

        Scaffold(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            containerColor = GrayBackground,
            topBar = {
                Surface(
                    color = White,
                    shadowElevation = 0.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (!useNavRail) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    // Transparent background app icon, adjusted to fit 32dp
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                            contentDescription = "App Logo",
                                            modifier = Modifier.size(56.dp) // Adjusted size to make the foreground graphic visible in the 32dp box
                                        )
                                    }
                                    Text(
                                        text = "StudySnap",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = BrandPurple,
                                        letterSpacing = (-0.5).sp
                                    )
                                }
                            }

                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = SearchBackground
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        tint = GraySubtle,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Search notes...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = GraySubtle
                                    )
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {
                if (!useNavRail) {
                    SnapStudyBottomBar(
                        currentRoute = currentRoute,
                        onNavigate = ::navigateTo
                    )
                }
            }
        ) { innerPadding ->
            SnapStudyNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(
    name = "SnapStudy App — Mobile",
    showBackground = true,
    widthDp = 400,
    heightDp = 800
)
@Composable
fun SnapStudyAppMobilePreview() {
    StudySnapTheme {
        SnapStudyApp(windowSizeClass = WindowWidthSizeClass.Compact)
    }
}

@Preview(
    name = "SnapStudy App — Tablet",
    showBackground = true,
    widthDp = 1000,
    heightDp = 700
)
@Composable
fun SnapStudyAppTabletPreview() {
    StudySnapTheme {
        SnapStudyApp(windowSizeClass = WindowWidthSizeClass.Expanded)
    }
}
