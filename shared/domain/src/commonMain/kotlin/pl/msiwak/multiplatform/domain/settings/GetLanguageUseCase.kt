package pl.msiwak.multiplatform.domain.settings

import pl.msiwak.multiplatform.data.local.store.LanguageStore

class GetLanguageUseCase(private val languageStore: LanguageStore) {
    operator fun invoke(): String {
        return languageStore.geLanguage()
    }
}