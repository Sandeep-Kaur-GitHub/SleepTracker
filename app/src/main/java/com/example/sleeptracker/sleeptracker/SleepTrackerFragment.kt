package com.example.sleeptracker.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sleeptracker.R
import com.example.sleeptracker.databinding.FragmentSleepTrackerBinding


class SleepTrackerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_sleep_tracker, container, false)

        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sleep_tracker, container, false)
    }

}