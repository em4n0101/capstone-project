package com.em4n0101.gamecollection.view.profile

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Game
import com.em4n0101.gamecollection.networking.NetworkingStatusChecker
import com.em4n0101.gamecollection.utils.toast
import com.em4n0101.gamecollection.view.gamedetails.GameDetailsActivity
import com.em4n0101.gamecollection.view.main.MainActivity
import com.em4n0101.gamecollection.view.topgames.TopGamesAdapter
import com.em4n0101.gamecollection.viewmodel.InnerSearchGamesByGenreViewModel
import kotlinx.android.synthetic.main.fragment_inner.*
import org.koin.android.ext.android.inject

class InnerFragment(private val genre: String) : Fragment() {
    private val viewModel: InnerSearchGamesByGenreViewModel by inject()
    private lateinit var listener: FragmentCommunication
    private val networkingStatusChecker by lazy {
        NetworkingStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    interface FragmentCommunication {
        fun onInput(input: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            genreRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setupObservables()
            downloadData()
        }
    }

    private fun downloadData() {
        networkingStatusChecker.performIfConnectedToInternet(::displayNotNetworkAvailableMessage) {
            viewModel.searchGamesForGenre(genre)
        }
    }

    private fun setupObservables() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer { value: Boolean ->
            loaderGenreAnimationView.isVisible = value
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            activity?.toast(getString(R.string.error_network_download_data))
        })
        viewModel.gamesFoundedLiveData.observe(viewLifecycleOwner, Observer { value: List<Game> ->
            updateUiWithGameList(value)
        })
    }

    private fun displayNotNetworkAvailableMessage() {
        activity?.toast(getString(R.string.error_message_not_network_available_get_data))
    }

    private fun updateUiWithGameList(listOfShows: List<Game>) {
        genreTextView.isVisible = listOfShows.isEmpty()
        genreTextView.text = getString(R.string.title_for_genre_recommendations, genre)
        if (genreRecyclerView != null) {
            val adapter = TopGamesAdapter(::listItemPressed)
            adapter.setData(listOfShows)
            genreRecyclerView.adapter = adapter
        }
    }

    private fun listItemPressed(game: Game, gameView: View) {
        view?.let {
            val intent = Intent(context, GameDetailsActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_GAME, game)

            val imagePair = Pair.create(gameView, "posterImageTransactionName")
            //val titlePair = Pair.create(titleView, "nameShowTransaction")

            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                imagePair
                //titlePair
            )
            startActivity(intent, activityOptions.toBundle())
        }
    }

    public fun setListener(listener: FragmentCommunication) {
        this.listener = listener
    }
}