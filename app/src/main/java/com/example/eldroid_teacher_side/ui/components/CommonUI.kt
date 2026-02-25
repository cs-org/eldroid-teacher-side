package com.example.eldroid_teacher_side.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.R
@Composable
fun BottomBar(){
    BottomAppBar(
        containerColor = Color.White,
        modifier = Modifier
            .border(width = 1.dp, color = Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    modifier = Modifier.size(32.dp),
                    tint = Color(0xFF004020),
                    contentDescription = null
                )
                Text (
                    text = "HOME",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF004020)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    modifier = Modifier.size(32.dp),
                    contentDescription = null
                )
                Text (
                    text = "SCHEDULES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Icon(
                    painter = painterResource(R.drawable.grad_hat),
                    modifier = Modifier.size(32.dp),
                    contentDescription = null
                )
                Text (
                    text = "GRADES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    modifier = Modifier.size(32.dp),
                    contentDescription = null
                )
                Text (
                    text = "PROFILE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}