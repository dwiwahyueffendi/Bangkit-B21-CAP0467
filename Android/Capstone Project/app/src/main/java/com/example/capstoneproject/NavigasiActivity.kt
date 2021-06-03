package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.capstoneproject.view.*

/*class NavigasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addFragment(HomeFragment.newInstance())
        binding.apply {
            bottomNavigation.show(0)
            bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_home))
            bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_history))
            bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_laporan))
            bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_account))
        }


        binding.bottomNavigation.setOnClickMenuListener {
            when (it.id){
                0 -> {
                    replaceFragment(HomeFragment.newInstance())
                }
                1 -> {
                    replaceFragment(HistoryFragment.newInstance())
                }
                2 -> {
                    replaceFragment(ReportingFragment.newInstance())
                }
                3 -> {
                    replaceFragment(ProfileFragment.newInstance())
                } else -> {
                replaceFragment(HomeFragment.newInstance())
            }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

    private fun addFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }
}*/

