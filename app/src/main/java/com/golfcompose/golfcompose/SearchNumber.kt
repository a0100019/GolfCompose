package com.golfcompose.golfcompose


import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.golfcompose.golfcompose.room.MainViewModel
import com.golfcompose.golfcompose.room.Member


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

//검색용 코드인듯
@Composable
fun RoomScreen2(
    allMembers: List<Member>,
    searchResults: List<Member>,
    viewModel: MainViewModel,
    navController: NavController
) {
    var searching by remember { mutableStateOf(false) }
    var showFirstLoginDialog by remember { mutableStateOf(false) }
    var number by remember { mutableStateOf("") }


    // 다이얼로그 표시
    FirstLoginDialog(
        showDialog = showFirstLoginDialog,
        onDismiss = { showFirstLoginDialog = false }, // 다이얼로그가 닫힐 때 showDialog 값을 변경하여 다이얼로그를 닫음
        onConfirm = {

            viewModel.insertMember(
                Member(
                    number,
                    number.take(4)
                )
            )
            navController.navigate("PersonalScreen/$number")
            showFirstLoginDialog = false // 다이얼로그가 닫힘

        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row( horizontalArrangement = Arrangement.Center) {
            Text(text = "010-",fontSize = 50.sp)
            Text(
                text =
                    if (number.length > 4) {
                        number.substring(0,4) + "-" + number.substring(4)
                    } else {
                        number
                    },
                fontSize = 50.sp
            )

        }

        Spacer(modifier = Modifier.size(50.dp))


        NumberPads(
            onNumberAdded = { addedNumber ->
                if(number.length < 8) {
                    number += addedNumber.toString()
                }
                if(number.length == 8) {
                    viewModel.findMember(number)
                }
            },
            onDeleted = { ->
                if (number.isNotEmpty()) {
                    number = number.substring(0, number.length - 1)
                }
            },
            onChecked = { ->

                if (number.isNotEmpty()) {

                    //searching = true
                    //findMember 하면 searchResults 값이 알아서 바뀜

//                    viewModel.findMember(number)

                    val firstResult = searchResults.firstOrNull()
                    Log.d("SearchNumber", firstResult.toString())

                    if (firstResult != null) {
                        Log.d("SearchNumber", "값이 있음 ${firstResult.memberNumber}")
                        //개인화면 이동
                        navController.navigate("PersonalScreen/$number")
                    } else {


                        Log.d("SearchNumber", "값이 없음")
                        showFirstLoginDialog = true


                    }



                }

            },
            numberDigit = number.length
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstLoginDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {

            Surface(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("기록이 없는 번호 입니다.\n첫 로그인을 하시겠습니까?", fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                onDismiss()
                            }
                        ) {
                            Text("취소")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                onDismiss()
                                onConfirm()
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

