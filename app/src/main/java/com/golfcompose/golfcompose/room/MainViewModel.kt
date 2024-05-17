package com.golfcompose.golfcompose.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application) : ViewModel() {

    val allMembers: LiveData<List<Member>>
    private val repository: MemberRepository
    val searchResults: MutableLiveData<List<Member>>

    init {
        val memberDb = MemberRoomDatabase.getInstance(application)
        val memberDao = memberDb.memberDao()
        repository = MemberRepository(memberDao)

        allMembers = repository.allMembers
        searchResults = repository.searchResults
    }

    //init 함수보다 뒤에 있어야 한다

    fun insertMember(member: Member) {
        repository.insertMember(member)
    }

    fun findMember(number: String) {
        repository.findMember(number)
    }

    fun updateMemberName(memberNumber: String, newMemberName: String) {
        repository.updateMemberName(memberNumber, newMemberName)
    }

    fun updateMemberTotalAttendance(memberNumber: String, newMemberTotalAttendance: Int) {
        repository.updateMemberTotalAttendance(memberNumber, newMemberTotalAttendance)
    }

    fun updateMemberMonthAttendance(memberNumber: String, newMemberMonthAttendance: Int) {
        repository.updateMemberMonthAttendance(memberNumber, newMemberMonthAttendance)
    }

    fun updateMemberCoffee(memberNumber: String, newMemberCoffee: Int) {
        repository.updateMemberCoffee(memberNumber, newMemberCoffee)
    }

    fun updateMemberFirstTime(memberNumber: String, newMemberFirstTime: Long) {
        repository.updateMemberFirstTime(memberNumber, newMemberFirstTime)
    }

    fun updateMemberMonthTime(memberNumber: String, newMemberMonthTime: Long) {
        repository.updateMemberMonthTime(memberNumber, newMemberMonthTime)
    }




//    fun deleteMember(name: String) {
//        repository.deleteMember(name)
//    }

}