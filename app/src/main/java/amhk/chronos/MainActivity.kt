package amhk.chronos

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawer = findViewById(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.main_navigation)
        navigationView.setNavigationItemSelectedListener {
            // FIXME: should just inform the lower layers of the user's intention

            for (i in 0 until navigationView.menu.size()) {
                val item = navigationView.menu.getItem(i)
                item.isChecked = it.title == item.title
            }

            drawer.closeDrawer(navigationView, true)
            when (it.title) {
                "foo" -> switchToFragment(FooListFragment::class.java)
                "bar"-> switchToFragment(BarFragment::class.java)
                "bar 2" -> switchToFragment(BarFragment::class.java)
                else -> false
            }
        }

        if (savedInstanceState == null) {
            // FIXME: should call lower layers instead of making the switch explicitly
            navigationView.menu.getItem(0).isChecked = true
            supportFragmentManager.transaction {
                val fragment = FooListFragment()
                add(R.id.main_container, fragment)
            }
        }
    }

    private fun <T> switchToFragment(x: Class<in T>): Boolean where T: Fragment {
        supportFragmentManager.transaction {
            val fragment = x.newInstance() as Fragment
            replace(R.id.main_container, fragment)
        }
        return true
    }
}

private fun FragmentManager.transaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}