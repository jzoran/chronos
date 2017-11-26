package amhk.chronos

import android.arch.persistence.room.*
import android.content.Context
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

internal const val ID_NOT_IN_DATABASE = 0L
private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "chronos"

@Database(entities = arrayOf(Foo::class), version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
internal abstract class ChronosDatabase : RoomDatabase() {
    abstract fun fooDao(): FooDao

    companion object {
        private var instance: ChronosDatabase? = null

        @Synchronized
        fun get(context: Context): ChronosDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        ChronosDatabase::class.java, DATABASE_NAME)
                        .build()
            }
            return instance!!
        }
    }
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