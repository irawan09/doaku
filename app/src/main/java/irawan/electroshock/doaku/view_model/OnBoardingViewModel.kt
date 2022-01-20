package irawan.electroshock.doaku.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import irawan.electroshock.doaku.utils.datastore.DataStorePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val dataStorePreference: DataStorePreference) : ViewModel() {
    private var onBoarding : MutableLiveData<Boolean> = MutableLiveData()

    fun setOnBoarding(set : Boolean){
        viewModelScope.launch {
            dataStorePreference.saveOnboarding(true)
        }
    }
    fun retrieveOnBoarding() : MutableLiveData<Boolean>{
        viewModelScope.launch(Dispatchers.IO) {
            dataStorePreference.fetchOnboarding().collectLatest{
                onBoarding.postValue(it)
            }
        }
        return onBoarding
    }
}