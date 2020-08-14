package com.em4n0101.gamecollection.view.topgames

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.response.Game
import com.em4n0101.gamecollection.networking.NetworkingStatusChecker
import com.em4n0101.gamecollection.utils.toast
import com.em4n0101.gamecollection.viewmodel.TopGamesViewModel
import kotlinx.android.synthetic.main.fragment_top_games.*
import org.koin.android.ext.android.inject

class TopGamesFragment : Fragment() {
    private val viewModel: TopGamesViewModel by inject()
    private val networkingStatusChecker by lazy {
        NetworkingStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            setupObservables()
        }

        requestTopGamesButton.setOnClickListener {
            networkingStatusChecker.performIfConnectedToInternet(::displayNotNetworkAvailableMessage) {
                viewModel.getTopGames()
            }
        }
    }

    private fun setupObservables() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer { value: Boolean ->
            //loaderAnimationView.isVisible = value
            println("LIVE-DATA loading $value")
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            // activity?.toast(getString(R.string.error_network_download_data))
            println("LIVE-DATA error $it")
        })
        viewModel.topGamesLiveData.observe(viewLifecycleOwner, Observer { value: List<Game> ->
            //updateUiWithShowList(value)
            println("LIVE-DATA games $value")
        })
    }

    private fun displayNotNetworkAvailableMessage() {
        activity?.toast(getString(R.string.error_message_not_network_available_get_data))
    }
}