package ig.core.android.view.ui.activity.navMain

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import ig.core.android.R
import ig.core.android.base.BaseActivity
import ig.core.android.databinding.ActivityNavigationMainBinding
import ig.core.android.di.Injection
import ig.core.android.viewmodel.MainViewModel

class NavigationMainActivity : BaseActivity<ActivityNavigationMainBinding, MainViewModel>() {
    override val mLayoutId = R.layout.activity_navigation_main
    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun initView() {

        //Init Host Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration= AppBarConfiguration(navController.graph)

        setSupportActionBar(mBinding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController){
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }
}