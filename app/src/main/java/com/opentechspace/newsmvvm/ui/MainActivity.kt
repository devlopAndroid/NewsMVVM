package com.opentechspace.newsmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.opentechspace.newsmvvm.ui.Fragment.Breaking
import com.opentechspace.newsmvvm.R
import com.opentechspace.newsmvvm.Repository.NewsRepository
import com.opentechspace.newsmvvm.ViewModel.NewsViewModel
import com.opentechspace.newsmvvm.ViewModel.ViewModelFactory
import com.opentechspace.newsmvvm.db.LocalDatabase
import com.opentechspace.newsmvvm.ui.Fragment.Saved
import com.opentechspace.newsmvvm.ui.Fragment.Search

class MainActivity : AppCompatActivity() {
    lateinit var repository: NewsRepository
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)

        repository = NewsRepository(LocalDatabase.getDatabase(this))
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NewsViewModel::class.java)

    }

}