package com.golfcompose.golfcompose

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemberDao {

    @Insert
    fun insertMember(member: Member)

    //전화번호 검색
    @Query("SELECT * FROM members WHERE memberNumber = :number")
    fun findMember(number: String): List<Member>

    @Update
    fun updateMember(member: Member)

    @Query("SELECT * FROM members")
    fun getAllProducts(): LiveData<List<Member>>

}