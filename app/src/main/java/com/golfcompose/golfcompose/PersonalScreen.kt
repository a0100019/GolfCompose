package com.golfcompose.golfcompose

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.golfcompose.golfcompose.room.MainViewModel
import com.golfcompose.golfcompose.room.MainViewModelFactory
import com.golfcompose.golfcompose.room.RankScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

//개인화면
@Composable
fun PersonalScreen(navController: NavController, number: String = "12345678") {

    val owner = LocalViewModelStoreOwner.current
// 다이얼로그 표시 여부를 제어하기 위한 상태 변수
    var showDialog by remember { mutableStateOf(false) }

    owner?.let {
        val viewModel: MainViewModel = viewModel(
            it,
            "MainViewModel",
            MainViewModelFactory(
                LocalContext.current.applicationContext
                        as Application
            )
        )

        // 다이얼로그 표시
        UpdateNameDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false }, // 다이얼로그가 닫힐 때 showDialog 값을 변경하여 다이얼로그를 닫음
            onConfirm = { newName ->
                // 여기서 새 이름으로 업데이트 작업 수행
                viewModel.updateMemberName(number, newName)
                showDialog = false // 다이얼로그가 닫힘
            }
        )


        // 사용자 정보 받아오기
        val searchResults by viewModel.searchResults.observeAsState(listOf())
        val allMembers by viewModel.allMembers.observeAsState(listOf())

// memberTotalAttendance 필드의 값을 기준으로 내림차순 정렬
        val sortedTotalResults = allMembers.sortedByDescending { it.memberTotalAttendance }
        val sortedMonthResults = allMembers.sortedByDescending { it.memberMonthAttendance }

// number에 해당하는 멤버의 인덱스 찾기
        val totalIndex = sortedTotalResults.indexOfFirst { it.memberNumber == number }
        val monthIndex = sortedMonthResults.indexOfFirst { it.memberNumber == number }


        // findMember 함수 호출
        viewModel.findMember(number)
        val firstResult = searchResults.firstOrNull()

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
                            modifier = Modifier.size(100.dp) // Size of the icon
                        )
                       Text(
                                "나의 등급",
                                fontSize = 30.sp
                            )
                    }
                    Column {
                        Row {
                            if (firstResult != null) {
                                Text(
                                    firstResult.memberName,
                                    fontSize = 30.sp
                                )
                            } else {
                                Text("이름")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "$number",
                                fontSize = 20.sp
                            )
                        }
                        // 다이얼로그 표시 여부를 변경하는 버튼
                        Button(
                            onClick = { showDialog = true }

                        ) {
                            Text("이름 변경")
                        }
                    }
                    Column {
                        Text(
                            "현재 시각",
                            fontSize = 30.sp
                        )
                        CurrentTimeText()

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
                            if (firstResult != null) {
                                Text(
                                    text = firstResult.memberMonthAttendance.toString(),
                                    fontSize = 30.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                            } else {
                                Text("?")
                            }

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
                                text = (monthIndex+1).toString(),
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
                            if (firstResult != null) {
                                Text(
                                    text = firstResult.memberTotalAttendance.toString(),
                                    fontSize = 30.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                            } else {
                                Text("?")
                            }
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
                                text = (totalIndex+1).toString(),
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
                        if (firstResult != null) {
                            Text(
                                text = if(firstResult.memberTotalAttendance%10 == 0) {
                                    "커피 획득!"
                                } else {
                                    (firstResult.memberTotalAttendance%10).toString() + "/10"
                                       },
                                fontSize = 50.sp
                            )
                        } else {
                            Text("??")
                        }
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
                        if (firstResult != null) {
                            Text(convertMillisToDateTimeString(firstResult.memberFirstTime), fontSize = 40.sp)
                        } else {
                            Text("???")
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("골프장에 머무른 시간", fontSize = 40.sp)
                        if (firstResult != null) {
                            Text(intervalOfTime(firstResult.memberFirstTime), fontSize = 40.sp)
                        } else {
                            Text("???")
                        }
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
                                    modifier = Modifier.size(50.dp) // Size of the icon
                                )
                                Text("커피 교환권", fontSize = 20.sp)
                            }
                            if (firstResult != null) {
                                Text(
                                    firstResult.memberCoffee.toString(),
                                    fontSize = 20.sp
                                )
                            } else {
                                Text("???")
                            }
                            Text("개", fontSize = 20.sp)

                            Column {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_golf_course_24),
                                    contentDescription = "PersonalIcon",
                                    tint = Color.Green, // Tint color for the icon
                                    modifier = Modifier.size(50.dp) // Size of the icon
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
                            .size(50.dp, 70.dp)
                            .weight(0.2f)
                            .align(Alignment.Bottom),
                        onClick = { navController.popBackStack() }
                    ) {
                        Text("확인", fontSize = 30.sp)
                    }

                }


            }
        }
    }




}



fun intervalOfTime(storedTime : Long) : String {

    // 현재 시간을 밀리초 단위로 가져옵니다.
    val currentTime: Long = System.currentTimeMillis()

    // 두 시간의 차이를 계산합니다.
    val diffMillis: Long = currentTime - storedTime

    // 밀리초를 시간과 분으로 변환하여 출력합니다.
    val hours = diffMillis / (1000 * 60 * 60) // 밀리초를 시간으로 변환
    val minutes = (diffMillis / (1000 * 60)) % 60 // 밀리초를 분으로 변환

    return "${hours}시간 ${minutes}분"
}


//입장 시간 Long > localDateTime 변환
fun convertMillisToDateTimeString(millis: Long): String {
    val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("MM/dd HH시 mm분")
    return localDateTime.format(formatter)
}


//이름 변경 다이얼로그
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNameDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            var newName by remember { mutableStateOf("") }

            Surface(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("새 이름") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                onDismiss()
                                onConfirm(newName)
                            }
                        ) {
                            Text("확인")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1400, heightDp = 900)
@Composable
fun PersonalScreenPreview() {

    Row {
        Column(modifier = Modifier
            .weight(0.3f)
            .fillMaxHeight()) {
//            RankScreen()
            Box() {

            }
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
                            "1111",
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
                    CurrentTimeText()

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
                    Text("0시간 0분", fontSize = 40.sp)
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
                    onClick = {  }
                ) {
                    Text("확인")
                }

            }


        }
    }

}