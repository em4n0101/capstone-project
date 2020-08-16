package com.em4n0101.gamecollection.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.view.profile.InnerFragment

class HomeFragment : Fragment() {
    private lateinit var fragmentGenre1: InnerFragment
    private lateinit var fragmentGenre2: InnerFragment
    private lateinit var fragmentGenre3: InnerFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentGenre1 = InnerFragment("sports")
        fragmentGenre2 = InnerFragment("action")
        fragmentGenre3 = InnerFragment("indie")

        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentGenre1, fragmentGenre1)
           // ?.replace(R.id.fragmentGenre2, fragmentGenre2)
           // ?.replace(R.id.fragmentGenre3, fragmentGenre3)
            ?.commit()
    }
}