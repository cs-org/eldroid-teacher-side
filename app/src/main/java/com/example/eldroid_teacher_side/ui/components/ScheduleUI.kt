package com.example.eldroid_teacher_side.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.util.generateCurrentWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyCalendarCard(){
    val weekDays = remember { generateCurrentWeek() }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val currentMonth = remember(selectedDate) {
        selectedDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.k_arrow_left),
                    contentDescription = null,
                    modifier = Modifier.size(23.dp)
                )
                Text(
                    text = currentMonth,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004020)
                )
                Icon(
                    painter = painterResource(R.drawable.k_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.size(23.dp)
                )

            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                weekDays.forEach { day ->
                    val isSelected = day.fullDate == selectedDate

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { selectedDate = day.fullDate }
                    ) {
                        Text(
                            text = day.dayName,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Surface(
                            shape = CircleShape,
                            color = if (isSelected) Color(0xFF004020) else Color.Transparent,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center){
                                Text (
                                    text = day.dayNumber,
                                    color = if (isSelected) Color.White else Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        if(isSelected){
                            Box(Modifier.size(4.dp).background(Color(0xFFC5A347), CircleShape))
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ScheduleCard(
    code: String,
    name: String,
    time: String,
    room: String,
    isActive: Boolean = false
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)){
            if(isActive){
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .fillMaxHeight()
                        .background(Color(0xFF004020))
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = code,
                    color = Color(0xFFC5A347),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Text(text = name,
                    color = Color(0xFF1B3D2F),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(R.drawable.clock),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = " $time ",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Icon(
                        imageVector = Icons.Outlined.Place,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = " $room ",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                modifier = Modifier.padding(16.dp).size(20.dp),
                tint = Color.LightGray
            )
        }
    }
}
