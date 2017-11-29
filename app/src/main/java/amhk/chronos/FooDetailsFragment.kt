package amhk.chronos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

internal class FooDetailsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_foodetails, container, false)

        view.findViewById<TextView>(R.id.foodetails_id).also {
            it.text = arguments.getString("id")
        }

        view.findViewById<TextView>(R.id.foodetails_value).also {
            it.text = arguments.getString("data")
        }

        view.findViewById<TextView>(R.id.foodetails_timestamp).also {
            it.text = arguments.getString("timestamp")
        }

        return view
    }

    internal companion object {
        fun newInstance(foo: Foo): FooDetailsFragment {
            val args = Bundle()
            args.putString("id", foo.id.toString())
            args.putString("data", foo.data)
            args.putString("timestamp", foo.timestamp.toString())
            val fragment = FooDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}