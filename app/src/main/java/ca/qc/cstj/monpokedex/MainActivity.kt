package ca.qc.cstj.monpokedex

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ca.qc.cstj.monpokedex.fragments.DetailPokemonFragment
import ca.qc.cstj.monpokedex.fragments.ListPokemonFragment
import ca.qc.cstj.monpokedex.fragments.TestFragment
import ca.qc.cstj.monpokedex.models.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    ListPokemonFragment.OnListFragmentInteractionListener,
    DetailPokemonFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(pokedexNo: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        changeFragment(ListPokemonFragment.newInstance(1), false)
    }

    override fun onListFragmentInteraction(item: Pokemon?) {
        //Toast.makeText(this, item!!.nom, Toast.LENGTH_LONG).show()
        changeFragment(DetailPokemonFragment.newInstance(item!!.pokemonNo))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeFragment(newFragment: Fragment, saveInBackStack: Boolean = true, animate: Boolean = true,
                               tag: String = newFragment.javaClass.name){
        try {
            val isPopped = supportFragmentManager.popBackStackImmediate(tag, 0)
            if(!isPopped && supportFragmentManager.findFragmentByTag(tag) == null) {
                //Le fragment n'est pas dans le back stack.
                val transaction = supportFragmentManager.beginTransaction()
                if(animate) {
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                }
                transaction.replace(R.id.contentFrame, newFragment, tag)

                if(saveInBackStack) {
                    transaction.addToBackStack(tag)
                }

                transaction.commit()
            } else {
                //Le fragment est dans le back stack.
            }

        } catch(e: IllegalStateException) {
            Log.e("MONERREUR",e.toString())
        }

    }
}
