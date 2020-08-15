package com.em4n0101.gamecollection.view.gamedetails

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Game
import com.em4n0101.gamecollection.model.ScreenShot
import com.em4n0101.gamecollection.model.response.GameDetailsResponse
import com.em4n0101.gamecollection.networking.NetworkingStatusChecker
import com.em4n0101.gamecollection.utils.removeHtmlTags
import com.em4n0101.gamecollection.utils.setupImageForViewHolder
import com.em4n0101.gamecollection.utils.toast
import com.em4n0101.gamecollection.view.main.MainActivity
import com.em4n0101.gamecollection.viewmodel.gamedetails.GameDetailsViewModel
import kotlinx.android.synthetic.main.activity_game_details.*
import org.koin.android.ext.android.inject

class GameDetailsActivity : AppCompatActivity() {
    private val viewModel: GameDetailsViewModel by inject()
    private val networkingStatusChecker by lazy {
        NetworkingStatusChecker(getSystemService(ConnectivityManager::class.java))
    }
    private var currentGame: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get game pass from previous activity
        val game: Game? = intent.getParcelableExtra(MainActivity.EXTRA_GAME)
        game?.let {
            collapsingToolbar.title = it.name
            collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
            currentGame = it
            updateUiWithGame(it)
            setupObservables(it)
            getAdditionalInfoForGame(it)
        }

        /*favoriteAnimationView.setOnClickListener {
            currentShow?.let {
                isFavorite = !isFavorite
                if (isFavorite) viewModel.saveShow(it) else viewModel.deleteShowByName(it.name)
                animateFavoriteView()
            }
        }*/
    }

    private fun updateUiWithGame(game: Game) {
        gameDetailTitleTextView.text = game.name
        game.clip?.let {
            playGameClip(it.clip)
        }
        setupImageForViewHolder(
            game.background_image,
            gameDetailImageView,
            loaderAnimationGamePosterView,
            true
        )
        game.short_screenshots?.let { setupScreenshots(it) }
    }

    private fun playGameClip(videoUrl: String) {
        val uri: Uri = Uri.parse(videoUrl)
        videoGameHeader.setVideoURI(uri)
        videoGameHeader.requestFocus()
        videoGameHeader.start()
        videoGameHeader.setOnPreparedListener {
            it.isLooping = true
            loaderAnimationGameHeaderVideoView.visibility = View.GONE
        }
    }

    private fun setupScreenshots(screenshotList: List<ScreenShot>) {
        if (gameDetailScreenshotsRecyclerView != null) {
            gameDetailSeasonsTextView.text = getString(R.string.screenshots_title)
            val adapter = ScreenshotAdapter(::listScreenshotItemPressed)
            adapter.setData(screenshotList)
            gameDetailScreenshotsRecyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
            false
            )
            gameDetailScreenshotsRecyclerView.adapter = adapter
        }
    }

    private fun listScreenshotItemPressed(screenShot: ScreenShot) {
        // TODO go to the image activity
    }

    private fun updateUiWithGameAdditional(gameAdditional: GameDetailsResponse) {
        gameDetailDescriptionExpandableTextView.text = gameAdditional.description.removeHtmlTags()
    }

    // Observables
    private fun setupObservables(game: Game) {
        viewModel.loadingLiveData.observe(this, Observer { value: Boolean ->
            loaderAnimationView.visibility = if (value) View.VISIBLE else View.GONE
        })
        viewModel.errorLiveData.observe(this, Observer {
            toast(getString(R.string.error_network_download_data))
        })
        viewModel.gameDetailsLiveData.observe(this, Observer { additionalInfo: GameDetailsResponse ->
            updateUiWithGameAdditional(additionalInfo)
        })
    }

    /**
     * Request supplemental information about the game (game description)
     */
    private fun getAdditionalInfoForGame(game: Game) {
        networkingStatusChecker.performIfConnectedToInternet(::displayNotNetworkAvailableMessage) {
            viewModel.getGameDetails(game.id ?: 0)
        }
    }

    private fun displayNotNetworkAvailableMessage() {
        this.toast(getString(R.string.error_message_not_network_available_more_data))
    }
}