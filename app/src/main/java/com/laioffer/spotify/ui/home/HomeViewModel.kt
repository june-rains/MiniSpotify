package com.laioffer.spotify.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laioffer.spotify.datamodel.Section
import com.laioffer.spotify.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// 加入这个notation, 会有对应factory来产生相应的dependency
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository):  ViewModel() {
    // 可以改值 -> 用于给ViewModel来使用
    private val _uiState = MutableStateFlow(HomeUiState(emptyList(), isLoading = true))
    // 不可以改值 -> 用于给View来使用
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()



    fun fetchHomeScreen() {
        viewModelScope.launch {
            val sections = repository.getHomeSections()

            _uiState.value = HomeUiState(sections, isLoading = false)
            // 如果只有一个值发生改变，我们可以使用copy, 如果所有都改变，copy无所谓
            // _uiState.value = _uiState.value.copy(isLoading = false)
            Log.d("HomeViewModel", sections.toString())
        }
    }

}

data class HomeUiState(val feed: List<Section>, val isLoading: Boolean)