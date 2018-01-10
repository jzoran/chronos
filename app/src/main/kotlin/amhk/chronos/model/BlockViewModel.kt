package amhk.chronos.model

import amhk.chronos.database.ChronosDatabase
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

internal class BlockViewModel(app: Application) : AndroidViewModel(app) {
    val liveData: LiveData<List<Block>>

    init {
        val database = ChronosDatabase.get(getApplication())
        val dao = database.blockEntityDao()
        liveData = Transformations.map(dao.selectAll()) {
            listOfBlockEntities -> listOfBlockEntities as List<Block>
        }
    }
}