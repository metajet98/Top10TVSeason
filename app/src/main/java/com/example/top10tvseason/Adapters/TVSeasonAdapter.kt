package com.example.top10tvseason.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.top10tvseason.Helpers.Helper
import com.example.top10tvseason.Listeners.RecyclerViewCallBack
import com.example.top10tvseason.Models.TVSeason
import com.example.top10tvseason.R
import com.squareup.picasso.Picasso
import java.net.URL

class TVSeasonAdapter(val context: Context?,var list: ArrayList<TVSeason>, val callback:RecyclerViewCallBack.ImageClickCallBack) : RecyclerView.Adapter<TVSeasonAdapter.TVSeasonViewHolder>()
{
    private val TAG= "TVSeasonAdapter"
    private val helper:Helper= Helper()


    public class TVSeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val itemImage: ImageView= itemView.findViewById(R.id.item_image)
        val itemTittle: TextView= itemView.findViewById(R.id.item_title)
        val itemUpdated: TextView= itemView.findViewById(R.id.item_updated)
        val itemName: TextView= itemView.findViewById(R.id.item_name)
        val itemPrice: TextView= itemView.findViewById(R.id.item_price)
        val itemReleaseDate: TextView= itemView.findViewById(R.id.item_releaseDate)
        val itemRights: TextView= itemView.findViewById(R.id.item_rights)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVSeasonViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_tvseason, parent, false)
        val tvSeasonViewHolder = TVSeasonViewHolder(view)
        return tvSeasonViewHolder
    }
    override fun onBindViewHolder(holder: TVSeasonViewHolder, position: Int) {

        Log.d(TAG, "onBindViewHolder $position")

        holder.itemTittle.setText(list.get(position).title)
        holder.itemUpdated.setText(list.get(position).updated)
        holder.itemName.setText(list.get(position).name)
        holder.itemPrice.setText(list.get(position).price)
        holder.itemRights.setText(list.get(position).rights)
        holder.itemReleaseDate.setText(list.get(position).releaseDate)


        //var imageTask= Helper.DownloadImageTask(holder.itemImage).execute(list.get(position).image)
        Picasso.with(context).load(list.get(position).image).into(holder.itemImage);


        // callback click to activity
        holder.itemImage.setOnClickListener {
            callback.goToDetail(list.get(position).link)
        }
    }

    override fun onViewRecycled(holder: TVSeasonViewHolder) {
        super.onViewRecycled(holder)
        Log.d(TAG, "onViewRecycled - ${holder.position}")

    }

    override fun getItemCount(): Int {
        return list.size
    }





}