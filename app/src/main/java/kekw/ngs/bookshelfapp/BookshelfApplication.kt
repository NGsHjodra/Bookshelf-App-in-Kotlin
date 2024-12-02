package kekw.ngs.bookshelfapp

import android.app.Application
import kekw.ngs.bookshelfapp.data.AppContainer
import kekw.ngs.bookshelfapp.data.DefaultAppContainer

class BookshelfApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}