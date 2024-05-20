package com.golfcompose.golfcompose

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.golfcompose.golfcompose.room.MainViewModel


@Composable
fun MainScreen(navController: NavController) {

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

        // 사용자 정보 받아오기
        val searchResults by viewModel.searchResults.observeAsState(listOf())
        val allMembers by viewModel.allMembers.observeAsState(listOf())

        //클릭 횟수 기억
        var titleClickNumber by remember { mutableIntStateOf(0) }
        var dataLoad by remember { mutableStateOf(false) }


        // 다이얼로그 표시
        DataLoadDialog(
            showDialog = dataLoad,
            onDismiss = { dataLoad = false }, // 다이얼로그가 닫힐 때 showDialog 값을 변경하여 다이얼로그를 닫음
            onConfirm = { newName ->



            }
        )



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
                Row(modifier = Modifier.weight(0.3f)) {
                    Column(modifier = Modifier.weight(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("출석",
                            fontSize = 50.sp,
                            modifier = Modifier.clickable {
                                titleClickNumber++
                            }
                        )
                        Text("전화번호를 입력해주세요", fontSize = 30.sp)
                    }
                    Column(modifier = Modifier.weight(0.2f)) {
                        Text("현재 시각", fontSize = 30.sp)
                        CurrentTimeText()
                    }
                }
                Row(modifier = Modifier.weight(0.7f)) {
                    SearchNumberScreen(navController = navController)
                }

            }
        }


    }


}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataLoadDialog(
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
                    Text("새 닉네임을 입력해주세요 (10글자 이하)", fontSize = 20.sp)
                    OutlinedTextField(
                        value = newName,
                        onValueChange = {
                            // 입력된 값이 10글자 이하인 경우에만 newName 업데이트
                            if (it.length in 1..10) {
                                newName = it
                            }
                        },
                        label = { Text("새 이름") },
                        singleLine = true, // 단일 라인으로 설정하여 엔터 키 입력 방지
                        keyboardActions = KeyboardActions {
                            // 엔터 키 입력 시 이벤트를 소비하여 다이얼로그가 닫히지 않도록 함
                            onDismiss()
                            onConfirm(newName)
                        }
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