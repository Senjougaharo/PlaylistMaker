package com.example.playlistmaker.settings.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.liveData.observe(viewLifecycleOwner) {

            binding.settingsDarkSwitch.isChecked = it
        }


        binding.shareApp.setOnClickListener {

            val url = getString(R.string.practicum)
            val intent = viewModel.createShareIntent(url)
            startActivity(Intent.createChooser(intent, "Поделиться ->"))

        }

        binding.supportRequest.setOnClickListener {


            val contact = getString(R.string.contact)
            val messageTheme = getString(R.string.message_theme_for_devs)
            val message = getString(R.string.message_for_devs)
            val intent = viewModel.supportRequestIntent(contact, messageTheme, message)
            startActivity(intent)

        }



        binding.userAgreement.setOnClickListener {

            val url = getString(R.string.practicum_offer)
            val intent = viewModel.userAgreementIntent(url)
            startActivity(intent)

        }

        binding.settingsDarkSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.switchTheme(isChecked)

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}