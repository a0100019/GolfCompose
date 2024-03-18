package com.golfcompose.golfcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
//    var number by remember { mutableStateOf("010-") }

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
                    Text("현재 시각", fontSize = 30.sp)
                    CurrentTimeText()
                }
            }
            SearchNumberScreen(navController = navController)

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




//
//@Preview(showBackground = true, widthDp = 2000, heightDp = 1200)
//@Composable
//fun PersonalScreenPreview() {
//    val navController = rememberNavController()
//    PersonalScreen(navController = navController)
//}
