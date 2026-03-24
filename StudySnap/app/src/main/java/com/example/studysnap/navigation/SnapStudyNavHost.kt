package com.example.studysnap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studysnap.model.Screen
import com.example.studysnap.ui.screens.FlashcardsScreen
import com.example.studysnap.ui.screens.HomeScreen
import com.example.studysnap.ui.screens.NoteScannerScreen
import com.example.studysnap.ui.screens.QuizModeScreen

@Composable
fun SnapStudyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToScanner = {
                    navController.navigate(Screen.Scanner.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToFlashcards = {
                    navController.navigate(Screen.Flashcards.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToQuiz = {
                    navController.navigate(Screen.Quiz.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Scanner.route) {
            NoteScannerScreen()
        }

        composable(Screen.Flashcards.route) {
            FlashcardsScreen()
        }

        composable(Screen.Quiz.route) {
            QuizModeScreen()
        }
    }
}
