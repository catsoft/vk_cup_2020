package com.c.v.data.db.user_groups

import androidx.room.Dao
import androidx.room.Query
import com.c.v.data.db.BaseDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface VKUserGroupDao : BaseDao<VKUserGroupEntity> {

    @Query("Select * from vk_user_group WHERE id = :id")
    fun getById(id : Int) : Single<VKUserGroupEntity>

    @Query("SELECT * from vk_user_group")
    fun getAll() : Flowable<List<VKUserGroupEntity>>

    @Query("DELETE FROM vk_user_group WHERE id = :id")
    fun deleteById(id: Int) : Completable

    @Query("DELETE FROM vk_user_group WHERE id in (:id)")
    fun deleteAllById(id: List<Int>) : Completable

}

