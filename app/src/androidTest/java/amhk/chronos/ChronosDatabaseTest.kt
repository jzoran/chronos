package amhk.chronos

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
        val allFoos = dao.allFoosOrderByTimestamp()
        assertEquals(4, allFoos.size)
        assertEquals("a", allFoos[0].data)
        assertEquals("b", allFoos[1].data)
        assertEquals("c", allFoos[2].data)
        assertEquals("d", allFoos[3].data)
    }
}