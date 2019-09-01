package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import rynkbit.tk.coffeelist.db.AppDatabase
import rynkbit.tk.coffeelist.db.dao.BaseDao
import java.util.*

abstract class BaseFacade<DatabaseEntity : Entity, Entity> : KoinComponent{
    protected val appDatabase by inject<AppDatabase>()

    private fun findDao(entityClass: Class<Entity>): BaseDao<DatabaseEntity> {
        val daoMethod = appDatabase::class.java.declaredMethods.find {
            it.name.toLowerCase(Locale.ROOT).contains(
                    entityClass.simpleName.toLowerCase(Locale.ROOT))
        }

        return daoMethod?.invoke(appDatabase) as BaseDao<DatabaseEntity>
    }

    protected fun findAll(entityClass: Class<Entity>): LiveData<List<Entity>>{
        val liveData = MutableLiveData<List<Entity>>()
        val dao = findDao(entityClass)

        dao.findAll().map {
            val entities = mutableListOf<Entity>()

            it.forEach {
                entities.add(it)
            }

            liveData.postValue(entities)

            return@map it
        }
                .subscribe()

        return liveData
    }

    protected fun delete(entity: DatabaseEntity, entityClass: Class<Entity>): MutableLiveData<Unit> {
        val liveData = MutableLiveData<Unit>()
        findDao(entityClass)
                .delete(entity)
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(Unit)
                }
                .subscribe()
        return liveData
    }
}