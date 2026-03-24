package com.example.studysnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.studysnap.ui.SnapStudyApp
import com.example.studysnap.ui.theme.StudySnapTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudySnapTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                SnapStudyApp(windowSizeClass = windowSizeClass.widthSizeClass)
            }
        }
    }
}