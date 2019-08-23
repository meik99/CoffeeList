package rynkbit.tk.coffeelist.db.dao

import androidx.room.Insert
import io.reactivex.Flowable
import io.reactivex.Single

interface BaseDao<T> {
    @Insert
    fun insert(entity: T): Single<Long>

    fun findAll(): Flowable<List<T>>
}