package ca.qc.cstj.monpokedex.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ca.qc.cstj.monpokedex.POKEMON_IMG
import ca.qc.cstj.monpokedex.R


import ca.qc.cstj.monpokedex.fragments.ListPokemonFragment.OnListFragmentInteractionListener
import ca.qc.cstj.monpokedex.models.Pokemon
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.card_pokemon.view.*

class PokemonRecyclerViewAdapter(
    private val mValues: List<Pokemon>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<PokemonRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Pokemon
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        holder.bind(item)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var pokemon: Pokemon? = null
        val lblNomPokemon: TextView = mView.lblNomPokemon
        val imgPokemon : ImageView = mView.imgPokemon



        fun bind(pokemon: Pokemon){
            lblNomPokemon.text = pokemon.nom
            this.pokemon = pokemon

            val image = "$POKEMON_IMG${pokemon!!.pokemonNo}.png"
            Picasso.with(imgPokemon.context).load(image).fit().centerInside().into(imgPokemon)
        }
    }
}
