package com.phucduong.searchgitprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.phucduong.searchgitprofile.data.model.User
import com.phucduong.searchgitprofile.databinding.UserInfoItemBinding

class UserAdapter constructor(val onClickListener: ItemOnClickListener?) :
    ListAdapter<User, UserAdapter.UserViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.login == newItem.login
    }

    class UserViewHolder(val binding: UserInfoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UserInfoItemBinding.inflate(layoutInflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = getItem(position)
        holder.binding.user = currentUser
        holder.binding.onClickListener = onClickListener
        holder.binding.executePendingBindings()
    }
}

interface ItemOnClickListener {
    fun onClick(userLoginName: String)
}