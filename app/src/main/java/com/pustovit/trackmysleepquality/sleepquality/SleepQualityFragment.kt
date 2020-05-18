package com.pustovit.trackmysleepquality.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pustovit.trackmysleepquality.R
import com.pustovit.trackmysleepquality.databinding.SleepQualityFragmentBinding

class SleepQualityFragment : Fragment() {

    private lateinit var mViewModel: SleepQualityViewModel
    private lateinit var mBinding: SleepQualityFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.sleep_quality_fragment, container, false)


        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            SleepQualityFragmentViewModelFactory(arguments.sleepNightKey, application)

        mViewModel =
            ViewModelProvider(this, viewModelFactory).get(SleepQualityViewModel::class.java)

        mViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer { it: Boolean? ->
            if (it == true) {
                this.findNavController()
                    .navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                mViewModel.doneNavigation()
            }

        })

        mBinding.sleepQualityViewModel = mViewModel


        return mBinding.root
    }


}
