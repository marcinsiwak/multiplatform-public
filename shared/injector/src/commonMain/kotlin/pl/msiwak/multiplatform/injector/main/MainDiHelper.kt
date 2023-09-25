package pl.msiwak.multiplatform.injector.main

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pl.msiwak.multiplatform.ui.main.MainViewModel

class MainDiHelper : KoinComponent {
    private val mainVM: MainViewModel by inject()
    fun getMainViewModel(): MainViewModel = mainVM
    fun getNavigator() = mainVM.mainNavigator
}