package com.em4n0101.gamecollection.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.em4n0101.gamecollection.view.home.HomeFragment
import com.em4n0101.gamecollection.view.profile.ProfileFragment
import com.em4n0101.gamecollection.view.search.SearchFragment
import com.em4n0101.gamecollection.view.topgames.TopGamesFragment

class MainPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = listOf(
        //HomeFragment(),
        SearchFragment(),
        TopGamesFragment()
       // ProfileFragment()
    )
    private val titles = listOf(
       // "Home",
        "Search",
        "Top 20"
      //  "Profile"
    )

    override fun getCount(): Int = fragments.size
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}