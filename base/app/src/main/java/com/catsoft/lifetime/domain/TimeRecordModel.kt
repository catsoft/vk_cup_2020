package com.catsoft.lifetime.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_record")
data class TimeRecordModel(@PrimaryKey(autoGenerate = true) var id: Int = 0)