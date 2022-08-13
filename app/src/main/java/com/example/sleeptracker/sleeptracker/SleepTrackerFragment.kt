package com.example.sleeptracker.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sleeptracker.R
import com.example.sleeptracker.database.SleepDatabase
import com.example.sleeptracker.databinding.FragmentSleepTrackerBinding


class SleepTrackerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_sleep_tracker, container, false)
        val application= requireNotNull(this.activity).application
        val dataSource=SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory=SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel=ViewModelProvider(this,viewModelFactory).get(SleepTrackerViewModel::class.java)
        binding.lifecycleOwner=this
        binding.sleeptrackerViewModel=sleepTrackerViewModel
        return binding.root
    }
}