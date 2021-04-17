package com.app.omdbdemo.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(val app: Application) : AndroidViewModel(app) {
}