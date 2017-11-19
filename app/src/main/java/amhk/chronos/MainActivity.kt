package amhk.chronos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: FooViewModel
    private lateinit var adapter: FooAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = FooAdapter(this)

        viewModel = ViewModelProviders.of(this).get(FooViewModel::class.java)
        viewModel.fooObjects.observe(this, Observer<List<Foo>> {
            it?.let {
                adapter.setItems(it)
            }
        })

        findViewById<RecyclerView>(R.id.foo_list).also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        findViewById<Button>(R.id.insert_random_foo).apply {
            setOnClickListener({
                viewModel.insertRandomFoo()
            })
        }

        findViewById<Button>(R.id.delete_random_foo).apply {
            setOnClickListener({
                viewModel.deleteRandomFoo()
            })
        }
    }
}

internal class FooAdapter(private val context: Context,
                          private var items: List<Foo> = ArrayList()) :
        RecyclerView.Adapter<FooAdapter.ViewHolder>() {

    fun setItems(newItems: List<Foo>) {
        items = newItems
        // FIXME: replace inefficient notifyDataSetChanged with DiffUtils
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.foo_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.text)

        fun bind(item: Foo) {
            textView.text = "${item.id}: ${item.timestamp} ${item.data}"
        }
    }
}