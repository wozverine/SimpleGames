package com.glitch.simplegames.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.glitch.simplegames.R
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.data.repository.GameRepository
import com.glitch.simplegames.data.source.local.GameRoomDB
import com.glitch.simplegames.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {
	private val binding by viewBinding(FragmentSettingsBinding::bind)

	private lateinit var gameRepository: GameRepository
	private lateinit var sharedPref: SharedPreferences
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
		val gameDao = GameRoomDB.getInstance(requireContext()).gameDao()
		gameRepository = GameRepository(gameDao)
		with(binding) {
			btnReset.setOnClickListener {
				lifecycleScope.launch {
					gameRepository.clearScores()
					sharedPref.edit().putBoolean("firstTime", true).apply()
				}
			}
		}
	}
}