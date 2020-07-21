package com.example.medicalhub.activity

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.medicalhub.R
import com.example.medicalhub.model.Book
import com.example.medicalhub.until.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtBookName: TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var txtBookDsp: TextView
    lateinit var imgBookImage: ImageView
    lateinit var btnAddTOFavourite: Button
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLayout: RelativeLayout
lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var bookId: String? = "100"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtBookName = findViewById(R.id.txt1_ll_dsp)
        txtBookAuthor = findViewById(R.id.txt2_ll_dsp)
        txtBookPrice = findViewById(R.id.txt3_ll_dsp)
        txtBookRating = findViewById(R.id.txt_rating_dsp)
        txtBookDsp = findViewById(R.id.txt_dsp)
        imgBookImage = findViewById(R.id.img_dsp)
        btnAddTOFavourite = findViewById(R.id.btn_dsp)
        progressBar = findViewById(R.id.progressBar_dsp)
        progressBarLayout = findViewById(R.id.progressBarLayout_dsp)
        progressBar.visibility = View.VISIBLE
        progressBarLayout.visibility = View.VISIBLE

        toolbar= findViewById(R.id.toobar_dsp)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Book Detail"

        if (intent != null) {
            bookId = intent.getStringExtra("book_id")

        } else {
            finish()
            Toast.makeText(this@DescriptionActivity, "Gonna Somthing Wrong!", Toast.LENGTH_LONG)
        }
        if (bookId == "100") {
            finish()
            Toast.makeText(this@DescriptionActivity, "Gonna Somthing Wrong!", Toast.LENGTH_LONG)
        }
        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/fetch_books/"

        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)
        if (ConnectionManager().CheckConnectivity(this@DescriptionActivity)) {


            val JsonRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonParams,
                Response.Listener {

                    try {
                        val success = it.getBoolean("success")

                        if (success) {
                            val BookJsonObject = it.getJSONObject("book_data")
                            progressBarLayout.visibility = View.GONE
                            Picasso.get().load(BookJsonObject.getString("image"))
                                .error(R.drawable.hearder_image)
                                .into(imgBookImage)
                            txtBookName.text = BookJsonObject.getString("name")
                            txtBookAuthor.text = BookJsonObject.getString("author")
                            txtBookPrice.text = BookJsonObject.getString("price")
                            txtBookRating.text = BookJsonObject.getString("rating")
                            txtBookDsp.text = BookJsonObject.getString("Description")


                        } else {
                            Toast.makeText(
                                this@DescriptionActivity,
                                " data not fetched!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Volley error occurred! $e ",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                },
                Response.ErrorListener {

                    Toast.makeText(
                        this@DescriptionActivity,
                        "Gonna Somthing Wrong!",
                        Toast.LENGTH_LONG
                    ).show()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["content-type"] = "application/json"
                    headers["token"] = "8f89898d55fa1e"
                    return headers
                }

            }
            queue.add(JsonRequest)
        }
        else
        { val dialog= AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Network Not Available")
            dialog.setMessage("check your internet connection")
            dialog.setPositiveButton("Open Setting")
            {
                    text, listener->
                val settingIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                this?.finish()
            }
            dialog.setNegativeButton("close") {
                    text,listener->
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()

        }
    }
}