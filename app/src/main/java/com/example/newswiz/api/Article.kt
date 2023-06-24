package com.example.newswiz.api

import android.accounts.AuthenticatorDescription
import android.os.Parcelable
import java.security.CodeSource

data class Article(
    val title: String,
    val description: String,
    val urlToImage: String,
    )