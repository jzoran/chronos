package amhk.chronos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = ArrayList<String>()
        (1..100).mapTo(items) { "string $it" }
        val recyclerView : RecyclerView = findViewById(R.id.foo_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FooAdapter(this, items)
    }
}

class FooAdapter(private val context : Context, private val items : List<String>) : RecyclerView.Adapter<FooAdapter.ViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.foo_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String) {
            val textView : TextView = view.findViewById(R.id.text)
            textView.text = item
        }
    }
}