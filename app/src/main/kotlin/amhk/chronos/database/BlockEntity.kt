package amhk.chronos.database

import amhk.chronos.model.Block
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
internal data class BlockEntity(@PrimaryKey(autoGenerate = true) override val id: Long,
                                override val begin: OffsetDateTime,
                                override val end: OffsetDateTime) : Block {
    init {
        require(begin.isBefore(end))
    }
}