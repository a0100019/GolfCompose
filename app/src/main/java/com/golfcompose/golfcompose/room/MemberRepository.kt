package com.golfcompose.golfcompose.room

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

    fun updateMemberName(memberNumber: String, newMemberName: String) {
        coroutineScope.launch(Dispatchers.IO) {
            memberDao.updateMemberName(memberNumber, newMemberName)
        }
    }

    fun updateMemberTotalAttendance(memberNumber: String, newMemberTotalAttendance: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            memberDao.updateMemberTotalAttendance(memberNumber, newMemberTotalAttendance)
        }
    }

    fun updateMemberMonthAttendance(memberNumber: String, newMemberMonthAttendance: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            memberDao.updateMemberMonthAttendance(memberNumber, newMemberMonthAttendance)
        }
    }

    fun updateMemberCoffee(memberNumber: String, newMemberCoffee: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            memberDao.updateMemberCoffee(memberNumber, newMemberCoffee)
        }
    }

    fun updateMemberFirstTime(memberNumber: String, newFirstTime: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            memberDao.updateMemberFirstTime(memberNumber, newFirstTime)
        }
    }


    private fun asyncFind(number: String): Deferred<List<Member>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async memberDao.findMember(number)
        }

}