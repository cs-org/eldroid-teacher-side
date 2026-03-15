package com.example.eldroid_teacher_side.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.R

@Composable
fun ClassCard(onAttendanceClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Box(modifier = Modifier.padding(24.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.graduate),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.TopEnd)
                    .graphicsLayer(alpha = 0.1f),
                tint = MaterialTheme.colorScheme.onSecondary
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "ONGOING NOW",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "CS101: Computer Science I",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                ClassDetailItem(R.drawable.apartment, "Room RM 402 • Engineering Bldg")
                ClassDetailItem(R.drawable.clock, "09:00 AM - 10:30 AM")

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAttendanceClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(painter = painterResource(R.drawable.qr_code), contentDescription = null, modifier = Modifier.size(28.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Start Attendance", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ClassDetailItem(iconId: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
    }
}

@Composable
fun CourseCard(onAttendanceClick: () -> Unit, onGradesClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "CS202: Data Structures &\nAlgorithms",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "MWF",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoRow(R.drawable.clock, text = "01:00 PM - 02:30 PM")
                InfoRow(R.drawable.apartment, text = "Lab 3 • IT Building")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onAttendanceClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Attendance", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onGradesClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text("Grades", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun InfoRow(iconId: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}
