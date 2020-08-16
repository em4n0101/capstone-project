package com.em4n0101.gamecollection.view.gamedetails

import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Game
import com.em4n0101.gamecollection.model.Genre
import com.em4n0101.gamecollection.model.Platform
import com.em4n0101.gamecollection.model.ScreenShot
import com.em4n0101.gamecollection.model.response.GameDetailsResponse
import com.em4n0101.gamecollection.networking.NetworkingStatusChecker
import com.em4n0101.gamecollection.utils.*
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
    private var isFavorite: Boolean = false

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

        favoriteAnimationView.setOnClickListener {
            currentGame?.let {
                isFavorite = !isFavorite
                if (isFavorite) viewModel.saveGame(it) else viewModel.deleteGameById(it.id ?: 0)
                animateFavoriteView()
            }
        }
    }

    private fun animateFavoriteView() {
        currentGame?.let {
            favoriteAnimationView.apply {
                if (isFavorite) {
                    playAnimation()
                } else {
                    cancelAnimation()
                    progress = 0.0f
                }
            }
        }
    }

    private fun updateUiWithGame(game: Game) {
        gameDetailTitleTextView.text = game.name
        gameDetailRankTextView.text = getRankReadableForGame(game)
        gameDetailReleaseDateTextView.text = formatGameReleased(game)
        gameDetailPlaytimeTextView.text = formatPlayTime(game)
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
        game.parent_platforms?.let { setupPlatforms(it) }
        game.genres?.let { setupGenres(it) }
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
            gameDetailScreenshotsTextView.text = getString(R.string.screenshots_title)
            val adapter = ScreenshotAdapter(::listScreenshotItemPressed)
            adapter.setData(screenshotList.filter { screenShot -> screenShot.id != -1 })
            gameDetailScreenshotsRecyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
            false
            )
            gameDetailScreenshotsRecyclerView.adapter = adapter
        }
    }

    private fun setupPlatforms(platformList: List<Platform>?) {
        platformList?.let {
            gameDetailPlatformsTextView.text = getString(R.string.platforms_title)
            val adapter = PlatformAdapter()
            adapter.setData(it)
            gameDetailPlatformsRecyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            gameDetailPlatformsRecyclerView.adapter = adapter
        }
    }

    private fun setupGenres(genreList: List<Genre>) {
        gameDetailGenresTextView.text = getString(R.string.genres_title)
        val adapter = GenreAdapter()
        adapter.setData(genreList)
        gameDetailGenresRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        gameDetailGenresRecyclerView.adapter = adapter
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
        viewModel.getGameById(game.id ?: 0).observe(this, Observer {
            if (it != null) {
                isFavorite = true
                animateFavoriteView()
            }
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