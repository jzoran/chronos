package amhk.chronos

import android.arch.lifecycle.LiveData
import android.content.Context
import org.threeten.bp.OffsetDateTime
import java.util.Random
import java.util.concurrent.Executors

internal object Repository {
    private val random = Random()

    fun getFooObjects(context: Context): LiveData<List<Foo>> {
        return ChronosDatabase.get(context).fooDao().allDistinctFoosOrderByTimestamp()
    }

    fun insertRandomFoo(context: Context) {
        val index = random.nextInt(LOREM_IPSUM.size)
        val database = ChronosDatabase.get(context)
        ioThread {
            database.fooDao().insert(Foo(ID_NOT_IN_DATABASE, LOREM_IPSUM[index], OffsetDateTime.now()))
        }
    }

    fun deleteRandomFoo(context: Context) {
        val database = ChronosDatabase.get(context)
        ioThread {
            database.fooDao().deleteRandomFoo()
        }
    }
}

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(func: () -> Unit) {
    IO_EXECUTOR.execute(func)
}

private val LOREM_IPSUM = arrayOf(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "Sed pretium, lacus sit amet sodales pulvinar, metus lectus elementum ante, at gravida ante tortor dictum nisl.",
        "Nunc fringilla hendrerit condimentum.",
        "In eu augue id est rutrum pulvinar et nec nibh.",
        "Mauris molestie leo ex, ut venenatis eros viverra et.",
        "Fusce elementum tellus id nisl fringilla vehicula.",
        "Nunc nec auctor lectus, et gravida nibh.",
        "Nulla facilisi.",
        "Integer neque nisl, congue eu nulla ac, posuere sollicitudin dui.",
        "Donec erat felis, ultricies in lobortis at, ornare a leo.",
        "In lorem ex, viverra in hendrerit ut, vehicula ut eros.",
        "In egestas nec ante in gravida.",
        "Duis luctus justo nec libero euismod, sit amet luctus enim iaculis.",
        "Ut pharetra ligula in magna scelerisque posuere.",
        "Nullam scelerisque, leo at vehicula bibendum, est ligula ultrices odio, eu dignissim lacus elit at tortor.",
        "Vestibulum in turpis est.",
        "Morbi aliquam rhoncus eros, et iaculis leo ultrices sed.",
        "Mauris tincidunt rhoncus urna eget ultrices.",
        "Fusce placerat purus in ultricies maximus.",
        "Phasellus a imperdiet nisi.",
        "Pellentesque tincidunt lorem vel ex porta, sit amet cursus magna blandit.",
        "Maecenas convallis interdum tristique.",
        "Integer eu mi nunc.",
        "Etiam pretium, risus ac molestie placerat, mauris tortor varius sapien, quis pellentesque arcu risus et eros.",
        "Quisque hendrerit tempus arcu, vitae imperdiet arcu bibendum et.",
        "Aliquam dignissim quis libero a convallis.",
        "Aenean elit ligula, gravida nec augue condimentum, tincidunt vestibulum est.",
        "Nulla sed pharetra leo, eget dapibus erat.",
        "Sed eleifend viverra enim, eu aliquam mauris lobortis in.",
        "Sed suscipit metus nec elit maximus maximus.",
        "Aenean odio metus, placerat non convallis non, sodales ut lorem.",
        "Donec nec libero turpis.",
        "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean rutrum enim et interdum gravida.",
        "Aenean tincidunt metus velit, a tincidunt est condimentum eget.",
        "Donec ut finibus quam, at fringilla enim.",
        "Nunc ante enim, gravida eget vestibulum a, faucibus nec quam.",
        "Ut eu faucibus nulla.",
        "Donec et elementum nisi.",
        "Fusce dignissim diam sit amet leo luctus, quis ornare magna pellentesque.",
        "Vestibulum vulputate quam sed tempus molestie.",
        "Aenean ultrices commodo accumsan.",
        "Aenean ut felis vel ipsum auctor imperdiet non in lectus.",
        "Curabitur ipsum tellus, hendrerit non tristique et, sollicitudin sed est.",
        "Sed iaculis porttitor purus id vulputate.",
        "Morbi nec rhoncus massa.",
        "Integer auctor lobortis felis.",
        "Quisque laoreet risus nec elit pellentesque lacinia.",
        "Sed faucibus tempor arcu eu semper.",
        "Sed et rhoncus ex, et lobortis lectus.",
        "Ut porta quam augue, nec lacinia nisl dignissim id.",
        "Ut quis efficitur quam.",
        "Quisque feugiat ut justo non tincidunt.",
        "Aliquam massa metus, imperdiet non mollis ac, placerat sit amet ligula."
)