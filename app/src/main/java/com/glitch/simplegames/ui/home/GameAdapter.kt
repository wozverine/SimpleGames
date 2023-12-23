package com.glitch.simplegames.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glitch.simplegames.data.model.response.GameUI
import com.glitch.simplegames.databinding.ItemGameRvBinding

class GameAdapter(
	private val onGameClick: (Int) -> Unit
) : ListAdapter<GameUI, GameAdapter.GameViewHolder>(ScoreDiffUtilCallBack()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
		return GameViewHolder(
			ItemGameRvBinding.inflate(LayoutInflater.from(parent.context), parent, false),
			onGameClick
		)
	}

	override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	class GameViewHolder(
		private val binding: ItemGameRvBinding,
		val onGameClick: (Int) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(gameUI: GameUI) {
			with(binding) {
				tvHighScore.text = gameUI.highScore.toString()
				btnGame.text = gameUI.title

				btnGame.setOnClickListener {
					onGameClick(gameUI.gameId)
				}
			}
		}
	}

	class ScoreDiffUtilCallBack : DiffUtil.ItemCallback<GameUI>() {
		override fun areItemsTheSame(oldItem: GameUI, newItem: GameUI): Boolean {
			return oldItem.gameId == newItem.gameId
		}

		override fun areContentsTheSame(oldItem: GameUI, newItem: GameUI): Boolean {
			return oldItem == newItem
		}
	}
}
