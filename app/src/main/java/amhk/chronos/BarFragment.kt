package amhk.chronos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

internal class BarFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bar, container, false)
    }

    internal companion object {
        fun newInstance(): BarFragment {
            return BarFragment()
        }
    }
}