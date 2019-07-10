package com.example.top10tvseason.ViewMode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.top10tvseason.Listeners.RepoListener
import com.example.top10tvseason.Models.TVSeason
import com.example.top10tvseason.Repositories.AppleTop10SeasonRss

class HomeViewModel : ViewModel()
{
    val TAG= "HomeViewModel"


    val listTVSeason: MutableLiveData<ArrayList<TVSeason>> = MutableLiveData()
    val appleTop10SeasonRss= AppleTop10SeasonRss()




    //interface callback
    var listener: RepoListener.ListTVSeason= object : RepoListener.ListTVSeason {
        override fun getListTVSeason(result: ArrayList<TVSeason>?) {
            listTVSeason.value= result
        }

    }

    fun loadOnCreate()
    {
        if(listTVSeason.value==null)
        {
            appleTop10SeasonRss.execute(listener)

        }

    }

}