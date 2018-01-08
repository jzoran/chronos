package amhk.chronos.ui

import amhk.chronos.database.Foo
import amhk.chronos.model.FooViewModel
import amhk.chronos.R
import amhk.chronos.database.ID_NOT_IN_DATABASE
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

internal class FooDetailsFragment : Fragment() {
    private var id: Long = ID_NOT_IN_DATABASE
    private lateinit var viewModel: FooViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        id = arguments.getLong("id", ID_NOT_IN_DATABASE)
        if (id == ID_NOT_IN_DATABASE) {
            throw IllegalArgumentException("fragment argument 'id' missing")
        }

        val view = inflater.inflate(R.layout.fragment_foodetails, container, false)

        viewModel = ViewModelProviders.of(this).get(FooViewModel::class.java)
        viewModel.getFooWithId(id).observe(this, Observer<Foo> {
            it?.let {
                view.findViewById<TextView>(R.id.foodetails_id).apply {
                    text = it.id.toString()
                }
                view.findViewById<TextView>(R.id.foodetails_value).apply {
                    text = it.data
                }
                view.findViewById<TextView>(R.id.foodetails_timestamp).apply {
                    text = it.timestamp.toString()
                }
            }
        })

        return view
    }

    internal companion object {
        fun newInstance(id: Long): FooDetailsFragment {
            val args = Bundle()
            args.putLong("id", id)
            val fragment = FooDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}