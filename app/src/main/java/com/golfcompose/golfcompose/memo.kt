//package com.golfcompose.golfcompose.room
//
//
//import android.app.Application
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
//import com.golfcompose.golfcompose.R
//import com.golfcompose.golfcompose.ui.theme.GolfComposeTheme
//
//
//class RoomActivity : ComponentActivity() {
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContent {
//            GolfComposeTheme {
//                // A surface container using the 'background' color from the theme
//                RankScreen()
//            }
//        }
//    }
//}
//
//@Composable
//fun RankScreen() {
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//
//        val owner = LocalViewModelStoreOwner.current
//
//        owner?.let {
//            val viewModel: MainViewModel = viewModel(
//                it,
//                "MainViewModel",
//                MainViewModelFactory(
//                    LocalContext.current.applicationContext
//                            as Application
//                )
//            )
//            ScreenSetup(viewModel)
//        }
//    }
//}
//
//@Composable
//fun TitleRow(head1: String, head2:String, head3:String, head4:String) {
//    Row(
//        modifier = Modifier
//            .background(MaterialTheme.colorScheme.background)
//            .fillMaxWidth()
//            .padding(5.dp)
//    ) {
//        Text(head1,
//            modifier = Modifier
//                .weight(0.1f))
//        Text(head2,
//            modifier = Modifier
//                .weight(0.2f))
//        Text(head3,
//            modifier = Modifier.weight(0.2f))
//        Text(head4,
//            modifier = Modifier.weight(0.1f))
//    }
//}
//
//@Composable
//fun MemberRow(rank: String, name: String, totalAttendance: Int) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(5.dp)
//    ) {
//        Text(rank, modifier = Modifier
//            .weight(0.1f))
//        Text(name, modifier = Modifier.weight(0.2f))
//        Text(totalAttendance.toString(), modifier = Modifier.weight(0.2f))
//        Icon(
//            painter = painterResource(id = R.drawable.baseline_golf_course_24),
//            contentDescription = "PersonalIcon",
//            tint = Color.Green, // Tint color for the icon
//            modifier = Modifier.weight(0.1f)
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomTextField(
//    title: String,
//    textState: String,
//    onTextChange: (String) -> Unit,
//    keyboardType: KeyboardType
//) {
//    OutlinedTextField(
//        value = textState,
//        onValueChange = { onTextChange(it)},
//        keyboardOptions = KeyboardOptions(
//            keyboardType = keyboardType
//        ),
//        singleLine = true,
//        label = { Text(title)},
//        modifier = Modifier.padding(10.dp),
//        textStyle = TextStyle(fontWeight = FontWeight.Bold,
//            fontSize = 30.sp)
//    )
//}
//
//@Composable
//fun ScreenSetup(viewModel: MainViewModel) {
//
//    val allMembers by viewModel.allMembers.observeAsState(listOf())
//    val searchResults by viewModel.searchResults.observeAsState(listOf())
//
//    RoomScreen(
//        allMembers = allMembers,
//        searchResults = searchResults,
//        viewModel = viewModel
//    )
//}
//
//@Composable
//fun RoomScreen(
//    allMembers: List<Member>,
//    searchResults: List<Member>,
//    viewModel: MainViewModel
//) {
//    var memberNumber by remember { mutableStateOf("") }
//    var memberTotalAttendance by remember { mutableStateOf("") }
//    var searching by remember { mutableStateOf(false) }
//    var sortByAttendance by remember { mutableStateOf(false) } // 출석 수를 내림차순으로 정렬할지 여부
//
//    val onNumberTextChange = { text: String ->
//        memberNumber = text
//    }
//    val onTotalAttendanceTextChange = { text: String ->
//        memberTotalAttendance = text
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        CustomTextField(
//            title = "Member Number",
//            textState = memberNumber,
//            onTextChange = onNumberTextChange,
//            keyboardType = KeyboardType.Text
//        )
//
//        CustomTextField(
//            title = "Total Attendance",
//            textState = memberTotalAttendance,
//            onTextChange = onTotalAttendanceTextChange,
//            keyboardType = KeyboardType.Number
//        )
//
//        //추가 버튼
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//        ) {
//            Button(
//                onClick = {
//                    if (memberTotalAttendance.isNotEmpty()) {
//                        viewModel.insertMember(
//                            Member(
//                                memberNumber,
//                                memberTotalAttendance.toInt()
//                            )
//                        )
//                        searching = false
//                    }
//                }
//            ) {
//                Text(text = "추가")
//            }
//
//            //찾기 버튼
//            Button(
//                onClick = {
//                    searching = true
//                    viewModel.findMember(memberNumber)
//                }
//            ) {
//                Text("찾기")
//            }
//
////클리어 버튼
//            Button(
//                onClick = {
//                    searching = false
//                    memberNumber = ""
//                    memberTotalAttendance = ""
//                }
//            ) {
//                Text("Clear")
//            }
//
////정렬 방식 바꾸기 버튼
//            Button(
//                onClick = {
//// 정렬 방식 변경
//                    sortByAttendance = !sortByAttendance
//                }
//            ) {
//                Text(if (sortByAttendance) "전체 출석" else "이번 달 출석")
//            }
//        }
//
//// 정렬된 리스트 가져오기
//        val sortedList = if (sortByAttendance) {
//            allMembers.sortedByDescending { it.memberTotalAttendance }
//        } else {
//            allMembers.sortedByDescending { it.memberMonthAttendance }
//        }
//
////내림차순으로 보기
//        LazyColumn(
//            Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//        ) {
//            item {
//                TitleRow(head1 = "순위", head2 = "닉네임", head3 = "출석 횟수", head4 = "등급")
//            }
//
//            items(sortedList) { member ->
//                // sortByAttendance에 따라 출석 횟수를 올바른 속성에 할당
//                val totalAttendance = if (sortByAttendance) {
//                    member.memberTotalAttendance
//                } else {
//                    member.memberMonthAttendance
//                }
//                MemberRow(
//                    rank = (sortedList.indexOf(member) + 1).toString(),
//                    name = member.memberNumber,
//                    totalAttendance = totalAttendance
//                )
//            }
//        }
//
////
//////그냥 모든 리스트
////            val list = if (searching) searchResults else allMembers
////
//////검색해서 보기
////        LazyColumn(
////            Modifier.fillMaxWidth().padding(10.dp)
////        ) {
////            item {
////                TitleRow(head1 = "순위", head2 = "닉네임", head3 = "전체 출석", head4 = "등급")
////            }
////
////            items(list) { member ->
////                MemberRow(
////                    rank = (list.indexOf(member) + 1).toString(),
////                    name = member.memberName,
////                    totalAttendance = member.memberTotalAttendance
////                )
////            }
////        }
//
//    }
//}
//
//
//class MainViewModelFactory(val application: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MainViewModel(application) as T
//    }
//}
