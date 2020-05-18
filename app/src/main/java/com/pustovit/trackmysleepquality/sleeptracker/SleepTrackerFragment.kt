package com.pustovit.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pustovit.trackmysleepquality.R
import com.pustovit.trackmysleepquality.databinding.SleepTrackerFragmentBinding

class SleepTrackerFragment : Fragment() {


    private lateinit var mViewModel: SleepTrackerViewModel
    private lateinit var mBinding: SleepTrackerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.sleep_tracker_fragment, container, false)


        val application = requireNotNull(this.activity).application

        mViewModel = ViewModelProvider(
            this, SleepTrackerFragmentFactory(application)
        ).get(SleepTrackerViewModel::class.java)

        mBinding.setLifecycleOwner(this)

        mBinding.sleepTrackerViewModel = mViewModel

        mViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(
                        night.nightId
                    )
                )
                mViewModel.doneNavigating()
            }
        })


        val adapter = SleepNightAdapter(SleepClickListener { sleepNightId: Long ->
            //Toast.makeText(activity,"ID is $sleepNightId", Toast.LENGTH_SHORT).show()
            mViewModel.onSleepNightClicked(sleepNightId)
        })

        mBinding.sleepList.adapter = adapter

        mViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        mViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                mViewModel.doneShowingSnackbar()
            }
        })



        mViewModel.navigateToSleepQualityDetail.observe(viewLifecycleOwner, Observer {night ->
            night?.let {
                findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityDetail(night))
                mViewModel.onSleepQualityDetailNavigated()
            }
        })
        return mBinding.root
    }


}
