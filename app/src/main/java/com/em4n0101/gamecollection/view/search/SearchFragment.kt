package com.em4n0101.gamecollection.view.search

import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Game
import com.em4n0101.gamecollection.networking.NetworkingStatusChecker
import com.em4n0101.gamecollection.utils.toast
import com.em4n0101.gamecollection.view.gamedetails.GameDetailsActivity
import com.em4n0101.gamecollection.view.main.MainActivity.Companion.EXTRA_GAME
import com.em4n0101.gamecollection.view.topgames.TopGamesAdapter
import com.em4n0101.gamecollection.viewmodel.search.SearchGameViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {
    private val viewModel: SearchGameViewModel by inject()
    private val networkStatusChecker by lazy {
        NetworkingStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            // setup recycler
            if (context?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
                searchGameRecyclerView.layoutManager = GridLayoutManager(context, 2)
            } else {
                searchGameRecyclerView.layoutManager = GridLayoutManager(context, 4)
            }
            setupObservables()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearchGame)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(R.id.search_src_text)
            editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            editText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            editText.hint = getString(R.string.hintSearchGame)

            searchView.setOnCloseListener { true }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = handlerOnQueryTextSubmit(query)

                override fun onQueryTextChange(newText: String?): Boolean = handlerOnQueryTextChange(newText)
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun handlerOnQueryTextSubmit(query: String?): Boolean {
        query?.let {
            if (it.isNotBlank()) searchFor(it)
        }
        return true
    }

    private fun handlerOnQueryTextChange(newText: String?): Boolean {
        val minCharactersToSearch = 3
        newText?.let {
            if (it.length >= minCharactersToSearch) {
                searchFor(it)
            }
            else {
                updateUiWithShowList(emptyList())
            }
        }
        return true
    }

    private fun displayNotNetworkAvailableMessage() {
        activity?.toast(getString(R.string.error_message_not_network_available_search))
    }

    private fun searchFor(inputToSearch: String) {
        networkStatusChecker.performIfConnectedToInternet(::displayNotNetworkAvailableMessage) {
            loaderSearchAnimationView.visibility = View.VISIBLE
            emptySearchGamesTextView.visibility = View.GONE
            viewModel.searchForAGame(inputToSearch)
        }
    }

    private fun setupObservables() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer { value: Boolean ->
            loaderSearchAnimationView.isVisible = value
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            activity?.toast(getString(R.string.error_network_download_data))
        })
        viewModel.gamesFoundedLiveData.observe(viewLifecycleOwner, Observer { value: List<Game> ->
            updateUiWithShowList(value)
        })
    }

    private fun updateUiWithShowList(listOfGames: List<Game>) {
        emptySearchGamesTextView.isVisible = listOfGames.isEmpty()
        if (searchGameRecyclerView != null) {
            val adapter = TopGamesAdapter(::listItemPressed)
            adapter.setData(listOfGames)
            searchGameRecyclerView.adapter = adapter
        }
    }

    private fun listItemPressed(game: Game, posterImage: View) {
        view?.let {
            val intent = Intent(context, GameDetailsActivity::class.java)
            intent.putExtra(EXTRA_GAME, game)
            val imagePair = Pair.create(posterImage, "posterImageTransactionName")
          //  val titlePair = Pair.create(titleView, "nameShowTransaction")

            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                imagePair
           //     titlePair
            )
            startActivity(intent, activityOptions.toBundle())
        }
    }

}