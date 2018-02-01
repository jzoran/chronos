package amhk.chronos.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
internal abstract class BlockEntityDao : BaseDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(blockEntity: BlockEntity)

    @Query("SELECT * FROM BlockEntity ORDER BY DATETIME(begin), DATETIME(end)")
    protected abstract fun selectAll_impl(): LiveData<List<BlockEntity>>

    fun selectAll(): LiveData<List<BlockEntity>> = selectAll_impl().getDistinct()

    @Query("SELECT * FROM BlockEntity WHERE id == :id")
    protected abstract fun selectById_impl(id: Long): LiveData<BlockEntity>

    fun selectById(id: Long): LiveData<BlockEntity> = selectById_impl(id).getDistinct()
}