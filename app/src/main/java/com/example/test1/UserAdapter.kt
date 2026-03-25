package com.example.test1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.userName)
        val userEmail: TextView = view.findViewById(R.id.userEmail)
        val userRole: TextView = view.findViewById(R.id.userRole)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.userName.text = "${user.first_name} ${user.last_name}".trim().ifEmpty { "N/A" }
        holder.userEmail.text = user.email.ifEmpty { "N/A" }
        holder.userRole.text = user.role.ifEmpty { "N/A" }
    }
}
