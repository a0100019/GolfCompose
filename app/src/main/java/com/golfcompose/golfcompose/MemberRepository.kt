package com.golfcompose.golfcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//메서드들
class MemberRepository(private val memberDao: MemberDao) {
    val allMembers: LiveData<List<Member>> = memberDao.getAllProducts()
    val searchResults = MutableLiveData<List<Member>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun insertMember(newmember: Member) {
        coroutineScope.launch(Dispatchers.IO) {
            memberDao.insertMember(newmember)
        }
    }

//    fun deleteMember(name: String) {
//        coroutineScope.launch(Dispatchers.IO) {
//            memberDao.deleteMember(name)
//        }
//    }

    fun findMember(number: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(number).await()
        }
    }

    private fun asyncFind(number: String): Deferred<List<Member>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async memberDao.findMember(number)
        }

}