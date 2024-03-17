package com.golfcompose.golfcompose.room


import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.golfcompose.golfcompose.NumberPads
import com.golfcompose.golfcompose.R


@Composable
fun SearchNumberScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val owner = LocalViewModelStoreOwner.current

        owner?.let {
            val viewModel: MainViewModel = viewModel(
                it,
                "MainViewModel",
                MainViewModelFactory(
                    LocalContext.current.applicationContext
                            as Application
                )
            )
            ScreenSetup2(viewModel, navController)
        }
    }
}

@Composable
fun TitleRow2(head1: String, head2:String, head3:String, head4:String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(head1,
            modifier = Modifier
                .weight(0.1f))
        Text(head2,
            modifier = Modifier
                .weight(0.2f))
        Text(head3,
            modifier = Modifier.weight(0.2f))
        Text(head4,
            modifier = Modifier.weight(0.1f))
    }
}

@Composable
fun MemberRow2(rank: String, name: String, totalAttendance: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(rank, modifier = Modifier
            .weight(0.1f))
        Text(name, modifier = Modifier.weight(0.2f))
        Text(totalAttendance.toString(), modifier = Modifier.weight(0.2f))
        Icon(
            painter = painterResource(id = R.drawable.baseline_golf_course_24),
            contentDescription = "PersonalIcon",
            tint = Color.Green, // Tint color for the icon
            modifier = Modifier.weight(0.1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField2(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it)},
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        label = { Text(title)},
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 30.sp)
    )
}

@Composable
fun ScreenSetup2(viewModel: MainViewModel, navController: NavController) {

    val allMembers by viewModel.allMembers.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    RoomScreen2(
        allMembers = allMembers,
        searchResults = searchResults,
        viewModel = viewModel,
        navController = navController
    )
}

@Composable
fun RoomScreen2(
    allMembers: List<Member>,
    searchResults: List<Member>,
    viewModel: MainViewModel,
    navController: NavController
) {
    var memberNumber by remember { mutableStateOf("") }
    var memberTotalAttendance by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }
    var sortByAttendance by remember { mutableStateOf(false) } // 출석 수를 내림차순으로 정렬할지 여부

    val onNumberTextChange = { text: String ->
        memberNumber = text
    }
    val onTotalAttendanceTextChange = { text: String ->
        memberTotalAttendance = text
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        var number by remember { mutableStateOf("010-") }

        Text(
            text = number,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 50.sp
        )

        NumberPads(
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

//        //추가 버튼
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//        ) {
//            Button(
//                onClick = {
//
//                        viewModel.insertMember(
//                            Member(
//                                number
//                            )
//                        )
//                        searching = false
//
//                }
//            ) {
//                Text(text = "추가")
//            }
//
//            //찾기 버튼
//            Button(
//                onClick = {
//                    searching = true
//                    viewModel.findMember(number)
//                }
//            ) {
//                Text("찾기")
//            }


////그냥 모든 리스트
//            val list = if (searching) searchResults else allMembers

//검색해서 보기
//            LazyColumn(
//                Modifier.fillMaxWidth().padding(10.dp)
//            ) {
//                item {
//                    TitleRow(head1 = "순위", head2 = "닉네임", head3 = "전체 출석", head4 = "등급")
//                }
//
//                items(list) { member ->
//                    MemberRow(
//                        rank = (list.indexOf(member) + 1).toString(),
//                        name = member.memberName,
//                        totalAttendance = member.memberTotalAttendance
//                    )
//                }
//            }
//
//        }

    }

}

