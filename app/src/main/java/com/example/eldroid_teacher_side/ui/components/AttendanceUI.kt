package com.example.eldroid_teacher_side.ui.components

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.util.DateModel
import com.example.eldroid_teacher_side.util.getCurrentWeekDays

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceCalendarHeader(){
    val weekDays = remember { getCurrentWeekDays() }

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weekDays.forEach { date ->
                    DateTime(date)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F0EA)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFF1B3D2F).copy(alpha = 0.1f))
            ){
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    tint = Color(0xFF1B3D2F),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Mark All Present",
                    color = Color(0xFF1B3D2F),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DateTime(date: DateModel){
    val backgroundColor = if (date.isToday) Color(0xFF1B3D2F) else Color(0xFFF3F6F9)
    val contentcolor = if (date.isToday) Color.White else Color(0xFF5A6B81)
    val borderColor = if (date.isToday) Color(0xFFC5A347) else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Surface(
            modifier = Modifier.size(width = 55.dp, height = 70.dp),
            color = backgroundColor,
            shape = RoundedCornerShape(12.dp),
            border = if (date.isToday) BorderStroke(2.dp, borderColor) else null
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = date.dayName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = contentcolor.copy(alpha = 0.7f)
                )
                Text(
                    text = date.dayNumber,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = contentcolor
                )
            }
        }
    }
}


@Composable
fun AttendanceSearchBar(query: String, onQueryChange: (String) -> Unit){
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Search student name or ID...")},
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF1B3D2F))
        },
        trailingIcon = {
            if(query.isNotEmpty()){
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1B3D2F),
            unfocusedBorderColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun AttendanceStudentCard(
    name: String,
    studentId: String,
    @DrawableRes imageRes: Int,
    selectedStatus: String,
    onStatusChange: (String) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1B3D2F)
                )

                Text(
                    text = "ID $studentId",
                    fontSize = 14.sp,
                    color = Color(0xFF5A6B81)
                )
            }

            Surface(
                color = Color(0xFFF3F6F9),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(45.dp)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusButton("P", selectedStatus == "P") { onStatusChange("P") }
                    StatusButton("L", selectedStatus == "L") { onStatusChange("L") }
                    StatusButton("A", selectedStatus == "A") { onStatusChange("A") }
                }
            }
        }
    }
}

@Composable
fun StatusButton(label: String, isSelected: Boolean, onClick: () -> Unit){
    Surface(
        modifier = Modifier
            .width(40.dp)
            .fillMaxHeight()
            .clickable { onClick() },
        color = if (isSelected) Color(0xFF2D5A47) else Color.Transparent,
        shape = RoundedCornerShape(6.dp)
    ){
        Box(contentAlignment = Alignment.Center){
            Text (
                text = label,
                color = if (isSelected) Color.White else Color(0xFF5A6B81),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}
