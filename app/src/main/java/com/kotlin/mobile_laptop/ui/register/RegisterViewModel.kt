package com.kotlin.mobile_laptop.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.mobile_laptop.data.repository.AuthRepository
import com.kotlin.mobile_laptop.model.User
import com.kotlin.mobile_laptop.model.UserResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    var userResponseLiveData = MutableLiveData<UserResponse>()
    var errorLiveData = MutableLiveData<String>()


    fun register (userName: String, password: String) {
        authRepository.register(userName, password)
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<UserResponse> {
                override fun onSuccess(userResponse: UserResponse) {
                    userResponseLiveData.postValue(userResponse)
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onError(e: Throwable?) {
                    errorLiveData.postValue(e?.message)
                }
            })
    }

    fun saveUserDataLocal(user: User){
        authRepository.saveUserDataLocal(user)
    }

}