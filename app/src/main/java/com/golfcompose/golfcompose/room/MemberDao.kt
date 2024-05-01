package com.golfcompose.golfcompose.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemberDao {

    // 기본키가 중복되면 알아서 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMember(member: Member)

    //전화번호 검색
    @Query("SELECT * FROM members WHERE memberNumber = :number")
    fun findMember(number: String): List<Member>

    // 멤버의 이름을 업데이트하는 쿼리
    @Query("UPDATE members SET memberName = :newName WHERE memberNumber = :memberNumber")
    fun updateMemberName(memberNumber: String, newName: String)

    @Query("UPDATE members SET memberCoffee = :newMemberCoffee WHERE memberNumber = :memberNumber")
    fun updateMemberCoffee(memberNumber: String, newMemberCoffee: Int)

    @Query("SELECT * FROM members")
    fun getAllProducts(): LiveData<List<Member>>

}
