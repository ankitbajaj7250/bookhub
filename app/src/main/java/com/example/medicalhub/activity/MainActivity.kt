package com.example.medicalhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.medicalhub.R
import com.example.medicalhub.fragment.Fragment_about
import com.example.medicalhub.fragment.Fragment_app_info
import com.example.medicalhub.fragment.Fragment_dashboard
import com.example.medicalhub.fragment.Fragment_favourite
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.Toolbar)
        frameLayout=findViewById(R.id.frameLayout)
        navigationView=findViewById(R.id.navigationView)
        SetUpToolbar()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frameLayout,
                Fragment_dashboard()
            )
            .addToBackStack("Dashboard")
            .commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title="Dashboard"

        val actionBarDrawerToggle= ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.opem_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if (previousMenuItem!=null) {
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it

            when(it.itemId)
            {
                R.id.deshboard ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            Fragment_dashboard()
                        )
                        .addToBackStack("Dashboard")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Dashboard"
                }
                R.id.favourite ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            Fragment_favourite()
                        )
                        .addToBackStack("Favorite")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Favorite"
                }
                R.id.profile ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            Fragment_about()
                        )
                        .addToBackStack("Profile")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Profile"
                }
                R.id.app_info ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            Fragment_app_info()
                        )
                        .addToBackStack("App Info")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="App Info"
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    fun SetUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Medical Hub"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id =item.itemId
        if (id==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}