package com.example.top10tvseason.Repositories

import android.os.AsyncTask
import android.util.Log
import com.example.top10tvseason.Listeners.RepoListener
import com.example.top10tvseason.Models.TVSeason
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class AppleTop10SeasonRss :AsyncTask<RepoListener.ListTVSeason, Void, String>()
{
    var listener: RepoListener.ListTVSeason? = null

    val TAG=" ModelGetData"
    override fun doInBackground(vararg p0: RepoListener.ListTVSeason?): String {
        this.listener= p0[0]
        val rssFeed= getXMLTop10Seasons("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topTvSeasons/limit=25/xml")

        return rssFeed
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        handleListTop10Seasons(result, listener)
    }

    fun handleListTop10Seasons(rssFeed: String, callback: RepoListener.ListTVSeason?): Boolean
    {
        val result= ArrayList<TVSeason>()
        var inEntry= false
        var status= true
        var textValue= ""




        try {
            val factory= XmlPullParserFactory.newInstance()
            factory.isNamespaceAware= true
            val xpp= factory.newPullParser()
            Log.d(TAG, rssFeed.reader().toString())
            xpp.setInput(rssFeed.reader())

            var eventType= xpp.eventType
            var currentRecord= TVSeason()

            while (eventType!= XmlPullParser.END_DOCUMENT)
            {
                val tagName= xpp.name?.toLowerCase()
                when(eventType)
                {
                    XmlPullParser.START_TAG-> {
                        Log.d(TAG, "parse:  Starting tag for: "+ tagName)
                        if(tagName== "entry")
                        {
                            inEntry= true
                        }

                    }

                    XmlPullParser.TEXT-> textValue= xpp.text
                    XmlPullParser.END_TAG->{
                        Log.d(TAG, "parse:  Ending tag for: "+ tagName)
                        if (inEntry) {
                            when (tagName) {
                                "entry" -> {
                                    result?.add(currentRecord)
                                    inEntry = false
                                    currentRecord = TVSeason()   // create a new object
                                }
                                "artist" -> currentRecord.artist=textValue
                                "title" -> currentRecord.title= textValue
                                "updated" -> currentRecord.updated = textValue
                                "id" -> currentRecord.id = textValue
                                "name" -> currentRecord.name = textValue
                                "price" -> currentRecord.price = textValue
                                "image" -> currentRecord.image= textValue
                                "rights" -> currentRecord.rights= textValue
                                "releaseDate" -> currentRecord.releaseDate= textValue
                                "link" -> currentRecord.link= xpp.getAttributeValue(null,"href")
                            }
                        }
                    }
                }
                eventType = xpp.next()
            }


        } catch (e: Exception)
        {
            e.printStackTrace()
            status= false

        }
        callback?.getListTVSeason(result)
        return status






    }

    fun getXMLTop10Seasons(urlPath: String?): String
    {
        val xmlResult= StringBuilder()
        try{
            val url= URL(urlPath)
            val connection: HttpURLConnection= url.openConnection() as HttpURLConnection
            val response = connection.responseCode
            Log.d(TAG, response.toString())

            val inputStream = connection.inputStream
            val inputStreamReader= InputStreamReader(inputStream)
            val reader= BufferedReader(inputStreamReader)


            val inputBuffer= CharArray(500)
            var charsRead=0
            while(charsRead>=0)
            {
                charsRead= reader.read(inputBuffer)
                if(charsRead>0)
                {
                    xmlResult.append(String(inputBuffer),0,charsRead)
                }
            }
            reader.close()
            Log.d(TAG, "Received ${xmlResult.length} bytes")


        } catch (e: MalformedURLException)
        {
            Log.d(TAG," download XML: Invalid URL"+ e.toString())
        } catch (e: SecurityException)
        {
            Log.d(TAG," download XML: SecurityException, Need Permisstion?"+ e.toString())

        }
        catch (e: IOException)
        {
            Log.d(TAG," download XML: IO Exception"+ e.toString())
        } catch (e: Exception)
        {
            Log.d(TAG," download XML: Unknown Exception"+ e.toString())
        }
        return xmlResult.toString()
    }

}
