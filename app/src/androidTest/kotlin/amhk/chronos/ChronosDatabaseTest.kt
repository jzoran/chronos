package amhk.chronos

import amhk.chronos.database.BlockEntity
import amhk.chronos.database.ChronosDatabase
import amhk.chronos.database.Foo
import amhk.chronos.database.ID_NOT_IN_DATABASE
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

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
        val foo = Foo(ID_NOT_IN_DATABASE, "test",
                OffsetDateTime.parse("1970-01-01T00:01:02+01:00"))
        dao.insert(foo)
        val allFoos = dao.allFoos()
        assertEquals(1, allFoos.size)
        assertEquals("test", allFoos[0].data)
        assertEquals("1970-01-01T00:01:02+01:00",
                allFoos[0].timestamp.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
    }

    @Test
    fun orderByTimestamp() {
        val dao = database.fooDao()
        dao.insert(Foo(ID_NOT_IN_DATABASE, "a",
                OffsetDateTime.parse("2017-10-17T07:23:19.120+00:00")))
        dao.insert(Foo(ID_NOT_IN_DATABASE, "b",
                OffsetDateTime.parse("2017-10-17T09:36:27.526+00:00")))
        dao.insert(Foo(ID_NOT_IN_DATABASE, "c",
                OffsetDateTime.parse("2017-10-17T11:01:12.972-02:00")))
        dao.insert(Foo(ID_NOT_IN_DATABASE, "d",
                OffsetDateTime.parse("2017-10-17T17:57:01.784+00:00")))
        val allFoos = getValue(dao.allDistinctFoosOrderByTimestamp())
        assertEquals(4, allFoos.size)
        assertEquals("a", allFoos[0].data)
        assertEquals("b", allFoos[1].data)
        assertEquals("c", allFoos[2].data)
        assertEquals("d", allFoos[3].data)
    }

    @Test(expected = IllegalArgumentException::class)
    fun failToCreateInvalidBlockEntity() {
        BlockEntity(ID_NOT_IN_DATABASE,
                OffsetDateTime.parse("2018-01-21T10:00:00.000Z"),
                OffsetDateTime.parse("2018-01-21T09:00:00.000Z"))
    }
}

internal fun <T> getValue(liveData: LiveData<T>): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T?) {
            data = t
            latch.countDown()
            liveData.removeObserver(this)
        }
    }
    liveData.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)
    return data!!
}