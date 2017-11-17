package amhk.chronos

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

internal class FooViewModel(app: Application) : AndroidViewModel(app) {
    val fooObjects: LiveData<List<Foo>> = Repository.getFooObjects(getApplication())

    fun insertRandomFoo() {
        Repository.insertRandomFoo(getApplication())
    }

    fun deleteRandomFoo() {
        Repository.deleteRandomFoo(getApplication())
    }
}