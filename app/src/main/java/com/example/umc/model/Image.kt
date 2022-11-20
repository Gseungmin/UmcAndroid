package com.example.umc.model

import android.net.Uri

data class Image (
        var title : String = "",
        var location : String = "",
        var uri: Uri,
)