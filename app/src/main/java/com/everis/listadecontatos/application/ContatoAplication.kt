package com.everis.listadecontatos.application

import android.app.Application
import com.everis.listadecontatos.helpers.HelperDB

class ContatoAplication : Application() {

    var helperDB: HelperDB? = null
        private set

    companion object{
        lateinit var instance: ContatoAplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}