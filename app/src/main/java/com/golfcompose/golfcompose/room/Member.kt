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

    @ColumnInfo(name = "memberFirstTime")
    var memberFirstTime: Long = Date().time

    @ColumnInfo(name = "memberMonthTime")
    var memberMonthTime: Long = Date().time

    constructor()

    constructor(memberNumber: String) {
        this.memberNumber = memberNumber
    }
    constructor(memberNumber: String, memberName: String) {
        this.memberNumber = memberNumber
        this.memberName = memberName
    }

    constructor(memberNumber: String, memberName: String, memberTotalAttendance: Int, memberMonthAttendance: Int, memberCoffee: Int) {
        this.memberNumber = memberNumber
        this.memberName = memberName
        this.memberTotalAttendance = memberTotalAttendance
        this.memberMonthAttendance = memberMonthAttendance
        this.memberCoffee = memberCoffee
    }

}