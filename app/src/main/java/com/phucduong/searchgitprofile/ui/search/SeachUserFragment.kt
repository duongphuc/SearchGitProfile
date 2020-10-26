package com.phucduong.searchgitprofile.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phucduong.searchgitprofile.R
import com.phucduong.searchgitprofile.adapter.ItemOnClickListener
import com.phucduong.searchgitprofile.adapter.UserAdapter
import com.phucduong.searchgitprofile.databinding.SearchFragmentBinding
import com.phucduong.searchgitprofile.util.handleError
import com.phucduong.searchgitprofile.util.hideKeyboard
import com.phucduong.searchgitprofile.viewmodel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : Fragment(), ItemOnClickListener {
    private lateinit var viewBinding: SearchFragmentBinding
    private val viewModel by viewModels<SearchUserViewModel>()
    private var loading = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private val THRESHOLD = 3

    companion object {
        const val SEARCHED_USER_NAME = "searched_user_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        viewModel.error.observe(
            this.viewLifecycleOwner,
            Observer(this@SearchUserFragment::handleError)
        )
        viewModel.listUser.observe(this.viewLifecycleOwner, Observer {
            val adapter = viewBinding.userListInfo.adapter
            if (adapter is UserAdapter) {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
            activity?.hideKeyboard(viewBinding.userListInfo)
            loading = false
        })
    }

    private fun setupListAdapter() {
        val userAdapter = UserAdapter(this)
        val viewManager = LinearLayoutManager(activity)
        viewBinding.userListInfo.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = userAdapter
        }
        setUpLoadMoreListener(viewManager)
    }

    private fun setUpLoadMoreListener(layoutManager: LinearLayoutManager) {
        viewBinding.userListInfo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = layoutManager.itemCount
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= lastVisibleItem + THRESHOLD) {
                    viewModel.searchUserWithQuery()
                    loading = true
                }
            }
        })
    }

    override fun onClick(userLoginName: String) {
        val bundle = Bundle()
        bundle.putString(SEARCHED_USER_NAME, userLoginName)
        Navigation.findNavController(view!!)
            .navigate(R.id.action_userListFragment_to_userProfileFragment, bundle)
    }
}
