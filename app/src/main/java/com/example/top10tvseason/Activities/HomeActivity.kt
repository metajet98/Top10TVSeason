package com.example.top10tvseason.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.top10tvseason.Adapters.TVSeasonAdapter
import com.example.top10tvseason.Listeners.RecyclerViewCallBack
import com.example.top10tvseason.Models.TVSeason
import com.example.top10tvseason.R
import com.example.top10tvseason.ViewMode.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private val TAG: String= "MainActivity"

    private lateinit var homeViewModel: HomeViewModel
    private var listTVSeason= ArrayList<TVSeason>()
    private lateinit var listTVSeasonAdapter: TVSeasonAdapter
    private lateinit var imageTVSeasonCallback: RecyclerViewCallBack.ImageClickCallBack
    private lateinit var listObserve: Observer<ArrayList<TVSeason>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        initObserve()
        initCallBack()
        initRecyclerView()


        homeViewModel.loadOnCreate()



    }
    private fun initObserve()
    {
        listObserve = Observer<ArrayList<TVSeason>> {

            Log.d(TAG, it.size.toString())
            handleObserChanged(it)

        }
        homeViewModel.listTVSeason.observe(this, listObserve)
    }
    private fun initCallBack()
    {
        imageTVSeasonCallback= object : RecyclerViewCallBack.ImageClickCallBack
        {
            override fun goToDetail(link: String) {
                val openURL= Intent(Intent.ACTION_VIEW)
                openURL.data= Uri.parse(link)
                openURL.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(openURL)
            }

        }
    }
    private fun initRecyclerView()
    {
        home_recyclerView.layoutManager=  LinearLayoutManager(this)
        listTVSeasonAdapter= TVSeasonAdapter(this,listTVSeason, imageTVSeasonCallback)
        home_recyclerView.adapter= listTVSeasonAdapter
    }
    private fun handleObserChanged(it: ArrayList<TVSeason>)
    {
        listTVSeasonAdapter.list= it
        listTVSeasonAdapter.notifyDataSetChanged()
    }


}
