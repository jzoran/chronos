package amhk.chronos

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

internal const val ID_NOT_IN_DATABASE = 0L
private const val DATABASE_VERSION = 1

@Database(entities = arrayOf(Foo::class), version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
internal abstract class ChronosDatabase : RoomDatabase() {
    abstract fun fooDao(): FooDao
}

internal object Converters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(value: OffsetDateTime?): String? {
        return value?.format(formatter)
    }
}
