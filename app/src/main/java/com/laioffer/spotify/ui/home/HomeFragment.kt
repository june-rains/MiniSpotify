package com.laioffer.spotify.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.laioffer.spotify.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class HomeFragment : Fragment() {
    // 虽然没有inject, 实际上还是会工作，原因是我们 HomeViewModel中加了
    // @Hilt...

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme(colors = darkColors()) {
                    HomeScreen(viewModel, onTap = {
                        Log.d("HomeFragment", "We tapped ${it.name}")
                        val direction = HomeFragmentDirections.actionHomeFragmentToPlaylistFragment(it)
                        findNavController().navigate(directions = direction)

                    })
                }
            }
        }
        // return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 为什么loading == true 的时候，才fetch
        // handle 几种case
        // 1.当我们屏幕进入系统 -> isLoading 一定为true -> fetch
        // 2.当我们点favorite 然后再点回来，不会再fetch, 用的是老的fragment显示,这个是只针对2个tab
        // 如果是多个tab，那情况就变得复杂了，因为你点击操作是不确定的
        // 3. 当我们rotate, activity/fragment 销毁， 重新创立，state没了
        // google 为了保留这个state, viewModel这个class会保留之前fragment的信息


        // 我们希望FragmentRetainScope, 但是没有
        // 就像我们使用 activityScope 和 activityRetainScope
        if (viewModel.uiState.value.isLoading) {
            viewModel.fetchHomeScreen()
        }
    }


}