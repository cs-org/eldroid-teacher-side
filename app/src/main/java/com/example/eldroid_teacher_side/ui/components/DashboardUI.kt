package com.example.eldroid_teacher_side.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.R

@Composable
fun DisplayProfile(@DrawableRes imageId: Int) {
    Image(
        painter = painterResource(imageId),
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(52.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF004020),
                shape = CircleShape
            )
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ClassCard(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF004020)) // Dark Green
    ) {
        Box(modifier = Modifier.padding(24.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.graduate),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.TopEnd)
                    .graphicsLayer(alpha = 0.1f),
                tint = Color.White
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "ONGOING NOW",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = Color(0xFFC5A347),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "CS101: Computer Science I",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                ClassDetailItem(R.drawable.apartment, "Room RM 402 • Engineering Bldg")
                ClassDetailItem(R.drawable.clock, "09:00 AM - 10:30 AM")

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { navController.navigate("attendance") },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5A347)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(painter = painterResource(R.drawable.qr_code), contentDescription = null, tint = Color(0xFF004020), modifier = Modifier.size(28.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Start Attendance", color = Color(0xFF004020), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ClassDetailItem(@DrawableRes imageId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(imageId),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp
        )
    }
}

@Composable
fun CourseCard(navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth() ,
        shape = RoundedCornerShape(24.dp), // Soft rounded corners
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                        color = Color(0xFF003D1B) // Dark green
                    ),
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = Color(0xFFF0F4F8),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "MWF",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A5568)
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
                    onClick = {
                        navController.navigate("attendance")
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003D1B)),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Attendance", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate("grades")
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF003D1B)),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFF003D1B))
                    Spacer(Modifier.width(8.dp))
                    Text("Grades", fontWeight = FontWeight.Bold, color = Color(0xFF003D1B))
                }
            }
        }
    }
}

@Composable
fun InfoRow(@DrawableRes imageId: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(imageId),
            contentDescription = null,
            tint = Color(0xFF718096),
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF718096)
        )
    }
}