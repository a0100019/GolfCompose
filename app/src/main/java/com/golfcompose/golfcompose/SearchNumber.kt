package com.golfcompose.golfcompose


import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.golfcompose.golfcompose.room.MainViewModel
import com.golfcompose.golfcompose.room.MainViewModelFactory
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        var number by remember { mutableStateOf("") }

        Row( horizontalArrangement = Arrangement.Center) {
            Text(text = "010-",fontSize = 50.sp)
            Text(
                text = number,
                fontSize = 50.sp
            )

        }

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

                if (number.isNotEmpty()) {

                    //searching = true
                    //findMember 하면 searchResults 값이 알아서 바뀜
                    viewModel.findMember(number)

                    val firstResult = searchResults.firstOrNull()
                    Log.d("SearchNumber", firstResult.toString())

                    if (firstResult != null) {
                        Log.d("SearchNumber", "값이 있음")
                        //개인화면 이동
                        navController.navigate("PersonalScreen/$number")
                    } else {
                        Log.d("SearchNumber", "값이 없음")
                        viewModel.insertMember(
                            Member(
                                number
                            )
                        )
                    }

                }

            }
        )
    }
}



