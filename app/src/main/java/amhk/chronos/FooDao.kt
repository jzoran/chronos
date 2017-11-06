package amhk.chronos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
internal interface FooDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(foo: Foo)

    @Query("SELECT * FROM Foo ORDER BY id")
    fun allFoos(): List<Foo>

    @Query("SELECT * FROM Foo ORDER BY datetime(timestamp), id")
    fun allFoosOrderByTimestamp(): List<Foo>
}