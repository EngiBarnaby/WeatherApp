package com.example.weatherapp.view.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherapp.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {

    private var _binding : FragmentTimerBinding? = null
    private val binding :FragmentTimerBinding
        get() {
            return _binding!!
        }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.let {
                setNum(it.getIntExtra(TIMER_NUM,0))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            receiver,
            IntentFilter(WAVE)
        )

        binding.timerBtn.setOnClickListener {
            val num = binding.editText.text.toString()
            if(num == ""){
                Toast.makeText(requireActivity(), "Введите число", Toast.LENGTH_SHORT).show()
            }

            if(num.toInt() < 1){
                Toast.makeText(requireActivity(), "Число должно быть больше нуля", Toast.LENGTH_SHORT).show()
            }
            else{
                startTimer(num.toInt())
            }
        }

    }

    private fun setNum(num : Int){
        binding.timerNumber.text = num.toString()
    }

    private fun startTimer(number : Int){
        requireActivity().startService(
            Intent(requireActivity(), TimerServiceIntent::class.java).apply {
                putExtra(BUNDLE_TIMER_KEY, number)
            }
        )
    }

}