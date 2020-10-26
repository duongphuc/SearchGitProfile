package com.phucduong.searchgitprofile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.phucduong.searchgitprofile.databinding.UserProfileFragmentBinding
import com.phucduong.searchgitprofile.ui.search.SearchUserFragment
import com.phucduong.searchgitprofile.viewmodel.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    private lateinit var viewBinding: UserProfileFragmentBinding
    private val viewModel by viewModels<UserProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = UserProfileFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUserName = arguments?.getString(SearchUserFragment.SEARCHED_USER_NAME)
        currentUserName?.let {
            viewModel.getUserProfile(currentUserName)
        }
    }
}