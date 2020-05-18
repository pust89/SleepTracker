package com.pustovit.trackmysleepquality.sleepqualitydetail

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
import com.pustovit.trackmysleepquality.databinding.SleepQualityDetailFragmentBinding

class SleepQualityDetail : Fragment() {


    private lateinit var mViewModel: SleepQualityDetailViewModel
    private lateinit var mBinding: SleepQualityDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.sleep_quality_detail_fragment,
            container,
            false
        )
        mBinding.setLifecycleOwner(this)

        val args = SleepQualityDetailArgs.fromBundle(requireArguments())
        val application = requireNotNull(activity).application
        val viewModelFactory = SleepQualityDetailViewModelFactory(args.sleepNightId, application)
        val mViewModel =
            ViewModelProvider(this, viewModelFactory).get(SleepQualityDetailViewModel::class.java)

        mBinding.viewModel = mViewModel

        mViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(SleepQualityDetailDirections.actionSleepQualityDetailToSleepTrackerFragment())
                mViewModel.onSleepTrackerNavigateDone()
            }
        })

        return mBinding.root
    }


}
