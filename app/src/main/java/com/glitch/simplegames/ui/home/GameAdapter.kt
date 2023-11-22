package com.glitch.simplegames.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.simplegames.databinding.ItemGameRvBinding

class GameAdapter(
    private val onGameClick: (String) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    private val gameList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.GameViewHolder {
        val binding =
            ItemGameRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding, onGameClick)
    }

    override fun onBindViewHolder(holder: GameAdapter.GameViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    class GameViewHolder(
        private val binding: ItemGameRvBinding, val onGameClick: (String) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(game: String){
            with(binding){

            }
        }
    }

}