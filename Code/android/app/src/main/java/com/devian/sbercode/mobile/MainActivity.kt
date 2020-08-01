package com.devian.sbercode.mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import com.devian.sbercode.mobile.ui.dashboard.DashboardFragment
import com.devian.sbercode.mobile.ui.reviews.ReviewsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val fragment1: Fragment = DashboardFragment()
    private val fragment2: Fragment = ReviewsFragment()

    private var activeFragment = fragment1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit()
        supportFragmentManager
            .beginTransaction().add(R.id.container, fragment1, "1").commit()

        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_dashboard -> {
                    supportFragmentManager
                        .beginTransaction().hide(activeFragment).show(fragment1).commit()
                    activeFragment = fragment1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_reviews -> {
                    supportFragmentManager
                        .beginTransaction().hide(activeFragment).show(fragment2).commit()
                    activeFragment = fragment2
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}