package amhk.chronos

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

internal const val ID_NOT_IN_DATABASE = 0L
private const val DATABASE_VERSION = 1

@Database(entities = arrayOf(Foo::class), version = DATABASE_VERSION, exportSchema = false)
internal abstract class ChronosDatabase : RoomDatabase() {
    abstract fun fooDao(): FooDao
}
