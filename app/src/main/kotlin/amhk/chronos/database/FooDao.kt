package amhk.chronos.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
internal abstract class FooDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(foo: Foo)

    @Query("SELECT * FROM Foo ORDER BY id")
    abstract fun allFoos(): List<Foo>

    @Query("SELECT * FROM Foo ORDER BY datetime(timestamp), id")
    protected abstract fun allFoosOrderByTimestamp(): LiveData<List<Foo>>

    fun allDistinctFoosOrderByTimestamp(): LiveData<List<Foo>> =
            allFoosOrderByTimestamp().getDistinct()

    @Query("DELETE FROM Foo WHERE id == (SELECT id FROM Foo ORDER BY RANDOM() LIMIT 1)")
    abstract fun deleteRandomFoo()

    @Query("SELECT * FROM Foo WHERE id == :id")
    abstract fun loadFooWithId(id: Long): LiveData<Foo>

    fun loadDistinctFooWithId(id: Long): LiveData<Foo> =
            loadFooWithId(id).getDistinct()

    private fun <T> LiveData<T>.getDistinct(): LiveData<T> {
        val distinctLiveData = MediatorLiveData<T>()
        distinctLiveData.addSource(this, object : Observer<T> {
            private var initialized = false
            private var lastObj: T? = null
            override fun onChanged(obj: T?) {
                if (!initialized || obj != lastObj) {
                    initialized = true
                    lastObj = obj
                    distinctLiveData.postValue(lastObj)
                }
            }
        })
        return distinctLiveData
    }
}
