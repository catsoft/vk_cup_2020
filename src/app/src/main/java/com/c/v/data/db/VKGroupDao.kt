package com.c.v.data.db

import androidx.room.*
import com.c.v.data.db.models.groups.VKGroupEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface VKGroupDao {

    @Insert
    fun insert(records: List<VKGroupEntity>): List<Long>

    @Insert
    fun insert(record: VKGroupEntity): Long

    @Query("SELECT * FROM vk_group")
    fun getGroups(): Flowable<List<VKGroupEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun replaceGroups(groups : List<VKGroupEntity>) : Completable
}