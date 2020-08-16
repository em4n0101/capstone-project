package com.em4n0101.gamecollection.view.profile

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Game
import com.em4n0101.gamecollection.view.gamedetails.GameDetailsActivity
import com.em4n0101.gamecollection.view.main.MainActivity.Companion.EXTRA_GAME
import com.em4n0101.gamecollection.view.topgames.TopGamesAdapter
import com.em4n0101.gamecollection.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            // setup recycler
            if (it.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                favoriteGamesRecyclerView.layoutManager = GridLayoutManager(it, 2)
            } else {
                favoriteGamesRecyclerView.layoutManager = GridLayoutManager(it, 4)
            }

            addGetGamesObservable()
        }
    }

    private fun addGetGamesObservable() {
        val observer = Observer<List<Game>> {
            if (it != null) {
                updateUiWithGameList(it)
            }
        }
        viewModel.getFavoriteGames().observe(viewLifecycleOwner, observer)
    }

    private fun updateUiWithGameList(listOfGames: List<Game>) {
        emptyFavoriteGamesTextView.visibility = if (listOfGames.isEmpty()) View.VISIBLE else View.GONE
        if (favoriteGamesRecyclerView != null) {
            val adapter = TopGamesAdapter(::listItemPressed)
            adapter.setData(listOfGames)
            favoriteGamesRecyclerView.adapter = adapter
        }
    }

    private fun listItemPressed(game: Game, posterImage: View) {
        view?.let {
            val intent = Intent(context, GameDetailsActivity::class.java)
            intent.putExtra(EXTRA_GAME, game)

            val imagePair = Pair.create(posterImage, "posterImageTransactionName")
           // val titlePair = Pair.create(titleView, "nameShowTransaction")
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                imagePair
             //   titlePair
            )
            startActivity(intent, activityOptions.toBundle())
        }
    }

}