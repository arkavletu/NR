package ru.netology.myrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.System.exit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setupWithNavController(navController)






    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_toolbar_menu,menu)
        val search = menu?.findItem(R.id.search)
        val searchView: SearchView = search?.actionView as SearchView
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId){
//            R.id.search -> true
//            R.id.filter -> true
//        }
//        return super.onOptionsItemSelected(item)
//    }

}