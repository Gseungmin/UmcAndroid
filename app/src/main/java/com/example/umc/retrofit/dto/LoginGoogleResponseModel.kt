package com.example.umc.retrofit.dto

import com.google.gson.annotations.SerializedName

data class LoginGoogleResponseModel(
    @SerializedName("access_token") var access_token: String,
)