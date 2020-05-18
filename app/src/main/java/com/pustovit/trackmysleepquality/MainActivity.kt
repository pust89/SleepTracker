package com.pustovit.trackmysleepquality

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.pustovit.trackmysleepquality.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)

        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(toolbar, navController)
    }

}
