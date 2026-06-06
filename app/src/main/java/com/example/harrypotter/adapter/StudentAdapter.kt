package com.example.harrypotter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.R
import com.example.harrypotter.dto.StudentDTO

class StudentAdapter(private var items: List<StudentDTO>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_student_photo)
        val tvName:  TextView  = view.findViewById(R.id.tv_student_name)
        val tvHouse: TextView  = view.findViewById(R.id.tv_student_house)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = items[position]
        holder.tvName.text  = student.name
        holder.tvHouse.text = student.house
        
        if (student.image.isNotEmpty()) {
            com.squareup.picasso.Picasso.get()
                .load(student.image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.ivPhoto)
        } else {
            holder.ivPhoto.setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<StudentDTO>) {
        items = newItems
        notifyDataSetChanged()
    }
}
