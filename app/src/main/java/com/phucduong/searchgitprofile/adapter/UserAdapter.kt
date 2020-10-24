package com.phucduong.searchgitprofile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.phucduong.searchgitprofile.data.local.User
import com.phucduong.searchgitprofile.databinding.UserInfoItemBinding

class UserAdapter constructor(
    private var listUser: List<User>
) : BaseAdapter() {
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        val binding: UserInfoItemBinding
        binding = if (view == null) {
            val inflater = LayoutInflater.from(viewGroup.context)
            UserInfoItemBinding.inflate(inflater, viewGroup, false)
        } else {
            DataBindingUtil.getBinding(view) ?: throw IllegalAccessException()
        }
        with(binding) {
            //info = listUser[position]
            executePendingBindings()
        }
        return binding.root
    }

    override fun getItem(position: Int) = listUser[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = listUser.size

    fun replaceData(listData: List<User>) {
        listUser = listData
        notifyDataSetChanged()
    }
}