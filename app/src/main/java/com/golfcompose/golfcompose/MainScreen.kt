package com.golfcompose.golfcompose

import android.app.Application
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
import com.golfcompose.golfcompose.room.Member
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


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
            showDialog = titleClickNumber % 2 == 1 && titleClickNumber > 4,
            onDismiss = { titleClickNumber++ }, // 다이얼로그가 닫힐 때 showDialog 값을 변경하여 다이얼로그를 닫음
            onConfirm = {
                Log.d("MainScreen", "onConfirm")

                val firestore = Firebase.firestore

//                val data1 = hashMapOf(
//                    "name" to "San Francisco",
//                    "state" to "CA",
//                    "country" to "USA",
//                    "capital" to false,
//                    "population" to 860000,
//                    "regions" to listOf("west_coast", "norcal"),
//                )
//                firestore.collection("number").document("SF").set(data1)


                firestore.collection("number")
                    .get()
                    .addOnSuccessListener { documents ->
                        Log.d("MainScreen", "${documents.size()}")
                        for (document in documents) {
                            Log.d("MainScreen", "${document.id} => ${document.data}")
                            val member = Member(
                                memberNumber = document.getString("number") ?: "",
                                memberTotalAttendance = document.getLong("totalAttendance")?.toInt() ?: 0,
                                memberMonthAttendance = document.getLong("monthAttendance")?.toInt() ?: 0,
                                memberName = document.getString("name") ?: "",
                                memberCoffee = document.getLong("coffee")?.toInt() ?: 0
                            )
                            viewModel.insertAllMember(member)
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("MainScreen", "Error getting documents: ", exception)
                    }


                Log.d("MainScreen", "end")

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
                    Text("데이터를 받아오겠습니까? 관계자 외 조작시, 데이터에 손상이 생긴 경우 법적 처벌을 받을 수 있습니다.", fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
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