package amhk.chronos

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
internal data class Foo(@PrimaryKey(autoGenerate = true) val id: Long, val data: String = "")