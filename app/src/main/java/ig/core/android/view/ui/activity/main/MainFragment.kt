package ig.core.android.view.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ig.core.android.R
import ig.core.android.view.ui.fragment.FragmentOne
import ig.core.android.view.ui.fragment.FragmentThree
import ig.core.android.view.ui.fragment.FragmentTwo

class MainFragment : AppCompatActivity(), FragmentTwo.OnFragmentCommunicationListener {
    lateinit var sChildFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)
        sChildFragmentManager = supportFragmentManager

        onReplaceFragment(FragmentOne())
    }

    private fun onReplaceFragment(fragment: Fragment) {
        sChildFragmentManager.beginTransaction()
                .replace(R.id.flMainContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    fun onReplaceFragmentStack(fragment: Fragment) {
        sChildFragmentManager.beginTransaction()
                .replace(R.id.flMainContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }

    override fun setData(str: String) {
        val frag = FragmentThree().also { it.setData(str) }
        onReplaceFragmentStack(frag)
    }
}