package amhk.chronos

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChronosDatabaseTest {
    private lateinit var database : ChronosDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getContext()
        database = Room.inMemoryDatabaseBuilder(context, ChronosDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun basicInsertAndGet() {
        val dao = database.fooDao()
        val foo = Foo(ID_NOT_IN_DATABASE, "test")
        dao.insert(foo)
        val allFoos = dao.allFoos()
        assertEquals(1, allFoos.size)
        assertEquals("test", allFoos[0].data)
    }
}