package com.golfcompose.golfcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.golfcompose.golfcompose.room.RankScreen
import com.golfcompose.golfcompose.ui.theme.GolfComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GolfComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationScreen()
                }
            }
        }
    }
}

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    // NavHost 설정
    NavHost(navController = navController, startDestination = "firstScreen") {
        composable("firstScreen") {
            MainScreen(navController = navController)
        }

        composable(
            route = "PersonalScreen/{number}",
            arguments = listOf(navArgument("number") { type = NavType.StringType })
        ) { backStackEntry ->
            val number = backStackEntry.arguments?.getString("number") ?: ""
            PersonalScreen(navController = navController, number = number)
        }

        //옮기는 변수 여러 개 일 때
//        composable(
//            route = "PersonalScreen/{name}/{age}",
//            arguments = listOf(
//                navArgument("name") { type = NavType.StringType },
//                navArgument("age") { type = NavType.IntType }
//            )
//        ) { backStackEntry ->
//            val name = backStackEntry.arguments?.getString("name") ?: ""
//            val age = backStackEntry.arguments?.getInt("age") ?: 0
//            PersonalScreen(navController = navController, name = name, age = age)
//        }

    }
}



@Composable
fun MainScreen(navController: NavController) {
    var number by remember { mutableStateOf("010-") }

    Row {
        Column(modifier = Modifier
            .weight(0.3f)
            .fillMaxHeight()) {
            RankScreen()
        }

        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Column(modifier = Modifier.weight(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("출석", fontSize = 50.sp)
                    Text("전화번호를 입력해주세요", fontSize = 30.sp)
                }
                Column(modifier = Modifier.weight(0.2f)) {
                    Text("현재 시각", fontSize = 20.sp)
                    Text("12시 24분", fontSize = 20.sp)
                }
            }

            Text(
                text = number,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 50.sp
            )

            NumberPads(number,
                onNumberAdded = { addedNumber ->
                    number += addedNumber.toString()
                },
                onDeleted = { ->
                    if (number.isNotEmpty()) {
                        number = number.substring(0, number.length - 1)
                    }
                },
                onChecked = { ->
                    navController.navigate("PersonalScreen/$number")
                }
            )
        }
    }
}


@Preview(showBackground = true, widthDp = 2000, heightDp = 1200)
@Composable
fun NumberPadsPreview() {
    GolfComposeTheme {
        NavigationScreen()
    }
}

//개인화면
@Composable
fun PersonalScreen(navController: NavController, number: String = "12345678") {

    Row {
        Column(modifier = Modifier
            .weight(0.3f)
            .fillMaxHeight()) {
            RankScreen()
        }

        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_golf_course_24),
                        contentDescription = "PersonalIcon",
                        tint = Color.Green, // Tint color for the icon
                        modifier = Modifier.size(150.dp) // Size of the icon
                    )
                    Text(
                        "나의 등급",
                        fontSize = 30.sp
                    )
                }
                Column {
                    Row {
                        Text(
                            "이름",
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            "(77113696)",
                            fontSize = 20.sp
                        )
                    }
                    Button(
                        onClick = { }) {
                        Text("이름 변경")
                    }
                }
                Column {
                    Text(
                        "현재 시각",
                        fontSize = 30.sp
                    )
                    Text(
                        "17시 34분",
                        fontSize = 30.sp
                    )
                }
            }

            Row {
                Column(modifier = Modifier.weight(0.6f)) {
                    Row(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "이번 달 출석",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            text = "1",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "회",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "이번달 순위",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "1",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "등",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

                    Row(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "전체 출석",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            text = "1",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "회",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "전체 순위",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "1",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = "등",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "추가 이벤트",
                        fontSize = 30.sp,
                    )
                    Text(
                        text = "출석 10회마다 커피 교환권을 드립니다.",
                        fontSize = 15.sp
                    )
                    Text(
                        text = "1/10",
                        fontSize = 50.sp
                    )
                }

            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("오늘의 출석 정보", fontSize = 30.sp)
                Text("나중에 다시 확인 가능합니다. 주차 시간에 참고해주세요", fontSize = 20.sp)
            }

            Row {
                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("오늘 입장 시간", fontSize = 40.sp)
                    Text("01/28 11시 53분", fontSize = 40.sp)
                }
                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("골프장에 머무른 시간", fontSize = 40.sp)
                    Text("11시간 59분", fontSize = 40.sp)
                }
            }

            Row {

                Column(
                    modifier = Modifier.weight(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("쿠폰함", fontSize = 30.sp)
                    Row {
                        Column {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_golf_course_24),
                                contentDescription = "PersonalIcon",
                                tint = Color.Green, // Tint color for the icon
                                modifier = Modifier.size(150.dp) // Size of the icon
                            )
                            Text("커피 교환권", fontSize = 20.sp)
                        }
                        Text("10", fontSize = 20.sp)
                        Text("개", fontSize = 20.sp)

                        Column {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_golf_course_24),
                                contentDescription = "PersonalIcon",
                                tint = Color.Green, // Tint color for the icon
                                modifier = Modifier.size(150.dp) // Size of the icon
                            )
                            Text("커피 교환권", fontSize = 20.sp)
                        }
                        Text("10", fontSize = 20.sp)
                        Text("개", fontSize = 20.sp)
                    }
                    Text("쿠폰을 사용하시려면 직원에게 말씀해주세요.")
                }

                Button(
                    modifier = Modifier
                        .size(100.dp, 100.dp)
                        .weight(0.2f)
                        .align(Alignment.Bottom),
                    onClick = { navController.popBackStack() }
                ) {
                    Text("확인")
                }

            }


        }
    }
}



//
//@Preview(showBackground = true, widthDp = 2000, heightDp = 1200)
//@Composable
//fun PersonalScreenPreview() {
//    val navController = rememberNavController()
//    PersonalScreen(navController = navController)
//}
