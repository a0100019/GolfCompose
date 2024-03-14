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

//    fun deleteMember(name: String) {
//        repository.deleteMember(name)
//    }

}