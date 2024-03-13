package com.golfcompose.golfcompose


import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.*
import androidx.compose.material3.Button
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.golfcompose.golfcompose.ui.theme.GolfComposeTheme
import com.golfcompose.golfcompose.Member


class RoomActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            GolfComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val owner = LocalViewModelStoreOwner.current

                    owner?.let{
                        val viewModel: MainViewModel = viewModel(
                            it,
                            "MainViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext
                                        as Application)
                        )
                        ScreenSetup(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun TitleRow(head1: String, head2:String, head3:String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(head1, color = Color.White,
            modifier = Modifier
                .weight(0.1f))
        Text(head2, color = Color.White,
            modifier = Modifier
                .weight(0.2f))
        Text(head3, color = Color.White,
            modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun MemberRow(number: String, name: String, totalAttendance: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(number, modifier = Modifier
            .weight(0.1f))
        Text(name, modifier = Modifier.weight(0.2f))
        Text(totalAttendance.toString(), modifier = Modifier.weight(0.2f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
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
fun ScreenSetup(viewModel: MainViewModel) {

    val allMembers by viewModel.allMembers.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    RoomScreen(
        allMembers = allMembers,
        searchResults = searchResults,
        viewModel = viewModel
    )
}


@Composable
fun RoomScreen(
    allMembers: List<Member>,
    searchResults: List<Member>,
    viewModel: MainViewModel
) {
    var memberNumber by remember { mutableStateOf("") }
    var memberTotalAttendance by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }

    val onNumberTextChange = { text : String ->
        memberNumber = text
    }
    val onTotalAttendanceTextChange = { text : String ->
        memberTotalAttendance = text
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomTextField(
            title = "Member Number",
            textState = memberNumber,
            onTextChange = onNumberTextChange,
            keyboardType = KeyboardType.Text)

        CustomTextField(
            title = "Total Attendance",
            textState = memberTotalAttendance,
            onTextChange = onTotalAttendanceTextChange,
            keyboardType = KeyboardType.Number
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Button(onClick = {
                if (memberTotalAttendance.isNotEmpty()) {
                    viewModel.insertMember(
                        Member(
                            memberNumber,
                            memberTotalAttendance.toInt()
                        )
                    )
                    searching = false
                }
            }) {
                Text(text = "추가")
            }

            Button(onClick = {
                searching = true
                viewModel.findMember(memberNumber)
            }) {
                Text("찾기")
            }

//            Button(onClick = {
//                searching = false
//                viewModel.deleteProduct(memberNumber)
//            }) {
//                Text("Delete")
//            }
//
//            Button(onClick = {
//                searching = false
//                memberNumber = ""
//                memberTotalAttendance = ""
//            }) {
//                Text("Clear")
//            }
        }

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)) {

            val list = if (searching) searchResults else allMembers

            item {
                TitleRow(head1 = "전화번호", head2 = "이름", head3 = "전체 출석")
            }

            //임포트가 안될 때 일단 된다고 가정하고 확실히 적어보기
            items(list) { member ->
                MemberRow(number = member.memberNumber, name = member.memberName, totalAttendance = member.memberTotalAttendance)
            }

        }
    }
}

class MainViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}
