package com.glitch.simplegames.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.simplegames.R
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.data.model.response.Game
import com.glitch.simplegames.data.source.local.Database
import com.glitch.simplegames.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()

    private val gameAdapter = GameAdapter(
        onGameClick = ::onGameClick
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectGame()



        with(binding){
            Database.addGames(1,"tictactoe",0)
            Database.addGames(1,"guess",0)
            gameAdapter.updateList(Database.getGames())
            rvGameList.adapter = gameAdapter

        }
        goToGame()
    }

    private fun goToGame() = with(binding) {
        viewModel.selectGameState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeViewModel.SelectGameState.GoToGame -> {
                    //findNavController()
                }

                else -> {}
            }
        }
    }

    private fun onGameClick(id: Int, title: String){
        Toast.makeText(requireContext(), title , Toast.LENGTH_SHORT).show()
    }
}