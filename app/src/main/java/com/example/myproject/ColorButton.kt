package com.example.myproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myproject.roomDB.ButtonColor
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorButton(viewModel: ColorViewModel) {
    val colors by viewModel.colors.collectAsState()
    val pendingSyncCount by viewModel.pendingSyncCount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.title),
                        fontSize = 24.sp,
                        lineHeight = 28.78.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.inria_sans))
                    )
                },
                actions = {
                    Button(
                        onClick = { viewModel.syncWithFirebase() },
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(android.graphics.Color.parseColor("#B6B9FF"))
                        )
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(
                                text = "$pendingSyncCount", fontSize = 20.sp,
                                lineHeight = 23.98.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(Font(R.font.inria_sans))
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.sync),
                                contentDescription = "sync",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable { viewModel.syncWithFirebase() },
                                tint = Color(android.graphics.Color.parseColor("#5659A4"))
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(android.graphics.Color.parseColor("#5659A4"))
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val randomColor = "#" + Integer.toHexString((Math.random() * 16777215).toInt())
                    val timestamp = System.currentTimeMillis()
                    viewModel.addColor(randomColor, timestamp)
                },
                modifier = Modifier
                    .size(123.dp, 35.dp)
                    .clip(RoundedCornerShape(20.dp)),
                containerColor = Color(android.graphics.Color.parseColor("#B6B9FF")),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(
                        stringResource(R.string.color),
                        fontSize = 18.sp,
                        lineHeight = 21.58.sp,
                        color = Color(android.graphics.Color.parseColor("#5659A4")),
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.inria_sans))
                    )
                    Icon(
                        painter = painterResource(R.drawable.add),
                        contentDescription = "Add",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(colors) { color ->
                ColorItem(color = color)
            }
        }
    }
}

@Composable
fun ColorItem(color: ButtonColor) {
    val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = Date()
    val formattedDate = date.format(currentDate)
    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(157.dp, 102.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                color = Color(android.graphics.Color.parseColor("#FFFCFC")),
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color(android.graphics.Color.parseColor(color.color))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = color.color.uppercase(),
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 21.58.sp
            )
            Divider(modifier = Modifier.border(
                        1.dp, color = Color(android.graphics.Color.parseColor("#FFFCFC"))).width(90.11.dp))
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ) {
            Text(
                stringResource(R.string.create),
                fontSize = 14.sp,
                lineHeight = 16.79.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.inria_sans)),
                modifier = Modifier.align(Alignment.End)
            )
            Text(
                "$formattedDate",
                fontSize = 14.sp,
                lineHeight = 16.79.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.inria_sans))
            )
        }
    }
}


/*

@Composable
@Preview(showBackground = true)
fun Preview() {
    ColorButton()
}*/
