package amhk.chronos.model

import org.threeten.bp.OffsetDateTime

internal interface Block {
    val id: Long
    val begin: OffsetDateTime
    val end: OffsetDateTime
}

internal val TIMESTAMP_NOT_SET = OffsetDateTime.MAX