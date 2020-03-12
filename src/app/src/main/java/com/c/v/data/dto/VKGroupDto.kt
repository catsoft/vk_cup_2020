package com.c.v.data.dto

data class VKGroupDto (
    val localId: Int,
    val remoteId: Int,
    val name : String,
    val screenName : String,
    val deactivated : String,
    val isMember : Boolean,
    val isClosed : Int,
    val photo50 : String,
    val photo100 : String,
    val photo200 : String,
    val isHiddenFromFeed : Boolean,
    val status : String,
    val members_count : Int,
    val description : String)