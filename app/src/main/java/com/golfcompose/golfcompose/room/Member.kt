package com.golfcompose.golfcompose.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "members")
class Member {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "memberNumber")
    var memberNumber: String = "12345678"

    @ColumnInfo(name = "memberName")
    var memberName: String = "임시 닉네임"

    @ColumnInfo(name = "memberTotalAttendance")
    var memberTotalAttendance: Int = 1

    @ColumnInfo(name = "memberMonthAttendance")
    var memberMonthAttendance: Int = 1

    @ColumnInfo(name = "memberCoffee")
    var memberCoffee: Int = 0
//
//    @ColumnInfo(name = "memberFirstTime")
//    var memberFirstTime: Date = Date()

    constructor()

    constructor(memberNumber: String, memberTotalAttendance: Int) {
        this.memberNumber = memberNumber
        this.memberTotalAttendance = memberTotalAttendance
    }

}