package ca.qc.cstj.monpokedex.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.cstj.monpokedex.POKEMON_URL
import ca.qc.cstj.monpokedex.R
import ca.qc.cstj.monpokedex.adapters.PokemonRecyclerViewAdapter

import ca.qc.cstj.monpokedex.models.Pokemon
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ListPokemonFragment.OnListFragmentInteractionListener] interface.
 */
class ListPokemonFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null
    private var pokemons: MutableList<Pokemon> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = PokemonRecyclerViewAdapter(pokemons, listener)
            }

            POKEMON_URL.httpGet().responseJson { request, response, result ->
                when(response.statusCode) {
                    200 -> {
                        createPokemonList(result.get())
                        view.adapter!!.notifyDataSetChanged()
                    }
                    else -> {

                    }
                }
            }
        }
        return view
    }

    private fun createPokemonList(json: Json) {
        pokemons.clear()
        //Array, car il retourne un tableau d'objet
        val tabPokemon = json.array()

        for (i in 0 until tabPokemon.length()) {
            pokemons.add(Pokemon(Json(tabPokemon[i].toString())))
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Pokemon?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ListPokemonFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
