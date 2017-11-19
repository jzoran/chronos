package amhk.chronos

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
internal data class Foo(@PrimaryKey(autoGenerate = true) val id: Long, val data: String = "", val timestamp: OffsetDateTime)