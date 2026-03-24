package com.example.studysnap.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysnap.model.Flashcard
import com.example.studysnap.ui.theme.*

@Composable
fun FlashcardsScreen(
    modifier: Modifier = Modifier
) {
    // Placeholder: these flashcards would come from AI generation via ViewModel
    // Placeholder for AI flashcard generation
    val sampleFlashcards = remember {
        listOf(
            Flashcard(1, "What is photosynthesis?", "The process by which green plants use sunlight to synthesize nutrients from carbon dioxide and water."),
            Flashcard(2, "What is the mitochondria?", "The powerhouse of the cell — an organelle responsible for cellular respiration and energy production."),
            Flashcard(3, "Define Newton's Second Law", "Force equals mass times acceleration (F = ma). The acceleration of an object depends on the net force and its mass.")
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }

    val currentCard = sampleFlashcards[currentIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Flashcards",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Text(
            text = "Card ${currentIndex + 1} of ${sampleFlashcards.size}",
            style = MaterialTheme.typography.labelLarge,
            color = GrayText
        )

        // Progress bar
        LinearProgressIndicator(
            progress = { (currentIndex + 1).toFloat() / sampleFlashcards.size },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = Teal,
            trackColor = GrayCard
        )

        // ── Flashcard ──
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(20.dp),
            color = White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentCard.question,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedVisibility(
                    visible = !showAnswer,
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Button(
                        onClick = { showAnswer = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Teal,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Show Answer", fontWeight = FontWeight.SemiBold)
                    }
                }

                AnimatedVisibility(
                    visible = showAnswer,
                    enter = fadeIn() + expandVertically()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 40.dp),
                            color = GrayBorder
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentCard.answer,
                            style = MaterialTheme.typography.bodyLarge,
                            color = DarkSubtext,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // ── Mark Known / Mark Weak ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Placeholder for spaced-repetition logic
            OutlinedButton(
                onClick = { /* Placeholder: Mark as weak */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                border = BorderStroke(1.dp, ErrorRed.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Mark Weak", fontWeight = FontWeight.Medium)
            }
            OutlinedButton(
                onClick = { /* Placeholder: Mark as known */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Teal),
                border = BorderStroke(1.dp, Teal.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Mark Known", fontWeight = FontWeight.Medium)
            }
        }

        // ── Navigation Controls ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (currentIndex > 0) {
                        currentIndex--
                        showAnswer = false
                    }
                },
                enabled = currentIndex > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GrayCard,
                    contentColor = DarkText
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
                Spacer(modifier = Modifier.width(6.dp))
                Text("Previous", fontWeight = FontWeight.Medium)
            }

            Button(
                onClick = {
                    if (currentIndex < sampleFlashcards.size - 1) {
                        currentIndex++
                        showAnswer = false
                    }
                },
                enabled = currentIndex < sampleFlashcards.size - 1,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonDark,
                    contentColor = White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Next", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

// ── Previews ──

@Preview(
    name = "Flashcards",
    showBackground = true,
    backgroundColor = 0xFFF8F9FC,
    widthDp = 400,
    heightDp = 800
)
@Composable
fun FlashcardsScreenPreview() {
    StudySnapTheme {
        FlashcardsScreen()
    }
}
