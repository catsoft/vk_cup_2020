package com.c.v.data.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface BaseDao<TEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity : TEntity) : Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entity: List<TEntity>) : Completable

    @Update
    fun update(entity: TEntity) : Completable

    @Update
    fun updateAll(entity: List<TEntity>) : Completable

    @Delete
    fun delete(entity: TEntity) : Completable

    @Delete
    fun deleteAll(entity: List<TEntity>) : Completable
}