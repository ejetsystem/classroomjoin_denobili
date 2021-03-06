package com.denobili.app.loginPage.signupGoogle

import android.content.Context
import com.denobili.app.R
import com.denobili.app.helper.RxFunctions
import com.denobili.app.helper_utils.ApiInterface
import com.denobili.app.signUpPage.SignUpApiResponse
import rx.Observer


class SignInGoogleApiManager {

    private var context: Context? = null

    fun postdata(otPverifyModel: SignInGoogleModel, apiInterface: ApiInterface, listener: SignInGoogleRequestListener, context11: Context?) {
        context = context11
        apiInterface.google_signup(otPverifyModel).compose(RxFunctions.applySchedulers()).subscribe(observer(listener))

    }


    fun observer(listener: SignInGoogleRequestListener): Observer<SignUpApiResponse> {
        return object : Observer<SignUpApiResponse> {
            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                listener.onRequestError(context!!.getString(R.string.serverError))
                e?.printStackTrace()
            }

            override fun onNext(t: SignUpApiResponse) {
                if (t?.status == "Success") {
                    listener.onRequestSuccesful()
                } else listener.onRequestError(t!!.error_message)

            }
        }
    }

    interface SignInGoogleRequestListener {

        fun onRequestSuccesful()

        fun onRequestError(error: String)

        fun onError(error: String)

    }

}