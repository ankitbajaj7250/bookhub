package com.example.medicalhub.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.medicalhub.R
import com.example.medicalhub.adaptor.DashboardRecyclerAdaptor
import com.example.medicalhub.model.Book
import com.example.medicalhub.until.ConnectionManager
import okhttp3.internal.http2.Settings
import org.json.JSONException
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Fragment_dashboard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerFragment_dashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLayout: LinearLayout
   var medicalList= arrayListOf<Book>()
    /* val medicalList = arrayListOf<Book>(
    Book("Agwani Medical","Himmaster","9414147250","5.0",R.drawable.hearder_image) ,
         Book("Abhishek Pharma","Nokha ","9636302626","5.0",R.drawable.hearder_image),
         Book("Krishana Medical","Himmaster","9352525811","5.0",R.drawable.hearder_image) ,
         Book("Maa Karni Medical","Himmaster","7733075811","5.0",R.drawable.hearder_image) ,
         Book("Bikaner Medicos","Himmaster","9784432897","5.0",R.drawable.hearder_image) ,
         Book("B.L.Pharma","Himmaster","9636307671","5.0",R.drawable.hearder_image) ,
         Book("Mittal Pharma","Himmaster","9413945270","5.0",R.drawable.hearder_image) ,
         Book("Tanwar Medicos","Himmaster","9414002056","5.0",R.drawable.hearder_image) ,
         Book("Bikaner Medicos","Himmaster","9314002056","5.0",R.drawable.hearder_image) ,
         Book("R.K.Pharma","Himmaster","9462113270","5.0",R.drawable.hearder_image)
     )*/

    lateinit var recyclerAdaptor: DashboardRecyclerAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
      recyclerFragment_dashboard= view.findViewById(R.id.recycle_dashboard)

        layoutManager= LinearLayoutManager(activity)
        progressBar=view.findViewById(R.id.progressBar)
        progressBarLayout=view.findViewById(R.id.progressBarLayout)
        progressBarLayout.visibility=View.VISIBLE


        recyclerAdaptor= DashboardRecyclerAdaptor(activity as Context,medicalList)

        recyclerFragment_dashboard.adapter = recyclerAdaptor
        recyclerFragment_dashboard.layoutManager= layoutManager
        recyclerFragment_dashboard.addItemDecoration(
            DividerItemDecoration(recyclerFragment_dashboard.context,(layoutManager as LinearLayoutManager).orientation)
        )

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books/"
        if (ConnectionManager().CheckConnectivity(activity as Context)) {

            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener {
                    try {

                        progressBarLayout.visibility=View.GONE
                        val success = it.getBoolean("success")
                        if (success) {
                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val bookjsonObject = data.getJSONObject(i)
                                val bookobject = Book(
                                    bookjsonObject.getString("bookId"),
                                    bookjsonObject.getString("bookName"),
                                    bookjsonObject.getString("bookAuthor"),
                                    bookjsonObject.getString("bookPrice"),
                                    bookjsonObject.getString("bookRating"),
                                    bookjsonObject.getString("bookImage")
                                )
                                medicalList.add(bookobject)
                            }

                        } else {
                            Toast.makeText(context, "somthing went wrong", Toast.LENGTH_LONG).show()
                        }
                    }
                    catch (
                        e: JSONException
                    )
                    {
                        Toast.makeText(context,"some unexpected error occurred",Toast.LENGTH_LONG).show()
                    }


                },
                Response.ErrorListener {
                    Toast.makeText(context, "volly Error occurred", Toast.LENGTH_LONG).show()

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val Header = HashMap<String, String>()
                    headers["content-type"] = "application/json"
                    headers["token"] = "8f89898d55fa1e"
                    return Header
                }

            }

            queue.add(jsonObjectRequest)
        }
        else
        {
            val dialog= AlertDialog.Builder(activity as Context)
            dialog.setTitle("Network Not Available")
            dialog.setMessage("check your internet connection")
            dialog.setPositiveButton("Open Setting")
            {
                text, listener->
                val settingIntent=Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("close") {
                text,listener->
                ActivityCompat.finishAffinity(activity as Activity)
            }
        dialog.create()
        dialog.show()
        }
        return view


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_dashboard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_dashboard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


