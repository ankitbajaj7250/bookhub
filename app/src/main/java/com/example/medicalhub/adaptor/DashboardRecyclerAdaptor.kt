package com.example.medicalhub.adaptor

import android.accounts.AccountManager.get
import android.content.Context
import android.media.CamcorderProfile.get
import android.nfc.tech.IsoDep.get
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R
import com.example.medicalhub.model.Book
import com.squareup.picasso.Picasso
import java.lang.reflect.Array.get
import java.nio.file.Paths.get
import java.util.ArrayList

class DashboardRecyclerAdaptor(val context:Context,val itemList : ArrayList<Book>) :
    RecyclerView.Adapter<DashboardRecyclerAdaptor.DashboardViewHolder>() {

    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val imageView:ImageView=view.findViewById(R.id.img_row)
        val textView1:TextView=view.findViewById(R.id.txt1)
        val textView2:TextView=view.findViewById(R.id.txt2)
        val textView3:TextView=view.findViewById(R.id.txt3)
        val rating:TextView=view.findViewById(R.id.rating)
        val layout_single :LinearLayout=view.findViewById((R.id.Layout_single))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_single_row_dashboard,parent,false)
    return DashboardViewHolder(view)
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val Medical= itemList[position]
        holder.textView1.text= Medical.bookName
        holder.textView3.text=Medical.bookPrice
        holder.textView2.text= Medical.bookAuthor
        holder.rating.text=Medical.bookRating
        //holder.imageView.setImageResource(Medical.bookImage)
        Picasso.get().load("Book.bookImage").error(R.drawable.hearder_image)
            .into(holder.imageView)
        holder.layout_single.setOnClickListener {
            Toast.makeText(context,"clicked on ${holder.textView1.text}",Toast.LENGTH_LONG).show()
        }



    }
}



