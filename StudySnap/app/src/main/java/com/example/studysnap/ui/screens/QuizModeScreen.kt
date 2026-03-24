package com.example.studysnap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysnap.model.QuizQuestion
import com.example.studysnap.ui.theme.*

@Composable
fun QuizModeScreen(
    modifier: Modifier = Modifier
) {
    // Placeholder: quiz questions would come from AI generation via ViewModel
    // Placeholder for AI quiz generation
    val sampleQuestions = remember {
        listOf(
            QuizQuestion(1, "What is the primary pigment in photosynthesis?", listOf("Melanin", "Chlorophyll", "Hemoglobin", "Keratin"), 1),
            QuizQuestion(2, "Which law states that energy cannot be created or destroyed?", listOf("Newton's First Law", "Law of Thermodynamics", "Ohm's Law", "Boyle's Law"), 1),
            QuizQuestion(3, "What is the powerhouse of the cell?", listOf("Nucleus", "Ribosome", "Mitochondria", "Golgi Body"), 2)
        )
    }

    // Show empty/subject-selection state first (matching reference)
    var hasStartedQuiz by remember { mutableStateOf(false) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    var answeredCount by remember { mutableIntStateOf(0) }
    var hasAnswered by remember { mutableStateOf(false) }

    if (!hasStartedQuiz) {
        // ── Quiz Subject Selection (matching reference) ──
        QuizSubjectsScreen(
            onStartQuiz = { hasStartedQuiz = true }
        )
    } else {
        // ── Active Quiz ──
        val currentQuestion = sampleQuestions[currentQuestionIndex]

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Quiz Mode",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            // Progress
            LinearProgressIndicator(
                progress = { (currentQuestionIndex + 1).toFloat() / sampleQuestions.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Teal,
                trackColor = GrayCard
            )

            Text(
                text = "Question ${currentQuestionIndex + 1} of ${sampleQuestions.size}",
                style = MaterialTheme.typography.labelLarge,
                color = GrayText
            )

            // Question card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = White,
                shadowElevation = 1.dp
            ) {
                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText,
                    modifier = Modifier.padding(24.dp)
                )
            }

            // Answer options
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                currentQuestion.options.forEachIndexed { index, option ->
                    val optionLabel = ('A' + index).toString()
                    val isSelected = selectedOption == index
                    val isCorrect = index == currentQuestion.correctIndex

                    val bgColor = when {
                        hasAnswered && isCorrect -> TealContainer
                        hasAnswered && isSelected && !isCorrect -> ErrorRedLight
                        isSelected -> TealLight
                        else -> White
                    }

                    val borderColor = when {
                        hasAnswered && isCorrect -> Teal
                        hasAnswered && isSelected && !isCorrect -> ErrorRed
                        isSelected -> Teal
                        else -> GrayBorder
                    }

                    Surface(
                        onClick = {
                            if (!hasAnswered) selectedOption = index
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = bgColor,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    if (!hasAnswered) selectedOption = index
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Teal,
                                    unselectedColor = GraySubtle
                                )
                            )
                            Text(
                                text = "Option $optionLabel:  $option",
                                style = MaterialTheme.typography.bodyLarge,
                                color = DarkText
                            )
                        }
                    }
                }
            }

            // Submit / Next
            if (!hasAnswered) {
                Button(
                    onClick = {
                        if (selectedOption >= 0) {
                            hasAnswered = true
                            answeredCount++
                            if (selectedOption == currentQuestion.correctIndex) score++
                            // Placeholder: update weak topics based on incorrect answers
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedOption >= 0,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonDark,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Text("Submit Answer", fontWeight = FontWeight.SemiBold)
                }
            } else {
                Button(
                    onClick = {
                        if (currentQuestionIndex < sampleQuestions.size - 1) {
                            currentQuestionIndex++
                            selectedOption = -1
                            hasAnswered = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = currentQuestionIndex < sampleQuestions.size - 1,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Teal,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Text(
                        if (currentQuestionIndex < sampleQuestions.size - 1) "Next Question" else "Quiz Complete",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Score
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = GrayCard
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Score: $score/$answeredCount",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Teal
                    )
                    // Placeholder: weak topics from AI analysis
                    Text(
                        text = "Weak Topics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = ErrorRed
                    )
                    Text(
                        text = "• Photosynthesis\n• Thermodynamics",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GrayText
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizSubjectsScreen(
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Quiz Subjects",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Text(
            text = "Choose a subject to test your knowledge with AI-generated quizzes tailored to your notes.",
            style = MaterialTheme.typography.bodyMedium,
            color = GrayText
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Empty state
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = GrayCard
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon circle
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(GrayBorder),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = GraySubtle
                    )
                }

                Text(
                    text = "No subjects yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Text(
                    text = "Create some notes first, then you can\ngenerate quizzes from them.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GrayText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Preview button to see the quiz demo
                OutlinedButton(
                    onClick = onStartQuiz,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Teal),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Teal)
                ) {
                    Text("Preview Demo Quiz", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

// ── Previews ──

@Preview(
    name = "Quiz — Subjects",
    showBackground = true,
    backgroundColor = 0xFFF8F9FC,
    widthDp = 400,
    heightDp = 700
)
@Composable
fun QuizModeScreenPreview() {
    StudySnapTheme {
        QuizModeScreen()
    }
}
