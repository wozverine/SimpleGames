package com.glitch.simplegames.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.glitch.simplegames.R
import com.glitch.simplegames.common.gone
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.common.visible
import com.glitch.simplegames.data.model.response.GameEntity
import com.glitch.simplegames.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

	private val binding by viewBinding(FragmentHomeBinding::bind)
	private val viewModel by viewModels<HomeViewModel>()
	private lateinit var sharedPref: SharedPreferences

	private val gameAdapter = GameAdapter(
		onGameClick = ::onGameClick
	)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

		/*val currentVersionCode = BuildConfig.VERSION_CODE
		val storedVersionCode = sharedPref.getInt("versionCode", -1)
		if (currentVersionCode > storedVersionCode) {
			val rockPaperScoreEntity =
				GameEntity(
					gameId = 2,
					title = getString(R.string.rock_paper),
					username = "q",
					highScore = 0
				)
			viewLifecycleOwner.lifecycleScope.launch {
				viewModel.addGame(rockPaperScoreEntity)
			}
			sharedPref.edit().putInt("versionCode", currentVersionCode).apply()
		}*/

		if (sharedPref.getBoolean("firstTime", true)) {
			val ticTacToeScoreEntity =
				GameEntity(
					gameId = 0,
					title = getString(R.string.tic_tac_toe),
					username = "q",
					highScore = 0
				)
			val guessTheNumberScoreEntity =
				GameEntity(
					gameId = 1,
					title = getString(R.string.guess_the_number),
					username = "q",
					highScore = 0
				)
			val rockPaperScoreEntity =
				GameEntity(
					gameId = 2,
					title = getString(R.string.rock_paper),
					username = "q",
					highScore = 0
				)
			viewLifecycleOwner.lifecycleScope.launch {
				viewModel.addGame(ticTacToeScoreEntity)
				viewModel.addGame(guessTheNumberScoreEntity)
				viewModel.addGame(rockPaperScoreEntity)
				sharedPref.edit().putBoolean("firstTime", false).apply()
				viewModel.getGames()
			}
		} else {
			viewModel.getGames()
		}

		goToGame()
		with(binding) {
			rvGameList.adapter = gameAdapter
			btnSettings.setOnClickListener {
				findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
			}
		}
	}


	private fun goToGame() = with(binding) {
		viewModel.selectGameState.observe(viewLifecycleOwner) { state ->
			when (state) {
				SelectGameState.Loading -> {
					rvGameList.gone()
				}

				is SelectGameState.SuccessState -> {
					gameAdapter.submitList(state.games)
					rvGameList.visible()
				}

				else -> {
					tvChooseGame.gone()
					rvGameList.gone()
				}
			}
		}
	}

	private fun onGameClick(id: Int) {
		if (id == 0) {
			findNavController().navigate(R.id.action_homeFragment_to_tictactoeFragment)
		}
		if (id == 1) {
			findNavController().navigate(R.id.action_homeFragment_to_guessTheNumberFragment)
		}
		if (id == 2) {
			findNavController().navigate(R.id.action_homeFragment_to_rockPaperFragment)
		}
	}
}