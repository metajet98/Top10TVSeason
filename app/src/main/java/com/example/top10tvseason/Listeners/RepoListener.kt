package com.example.top10tvseason.Listeners

import com.example.top10tvseason.Models.TVSeason

interface RepoListener {
    interface ListTVSeason
    {
        fun getListTVSeason(result: ArrayList<TVSeason>?)
    }


}