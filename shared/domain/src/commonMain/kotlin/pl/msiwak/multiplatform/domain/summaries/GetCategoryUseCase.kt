package pl.msiwak.multiplatform.domain.summaries

import pl.msiwak.multiplatform.commonObject.Category
import pl.msiwak.multiplatform.data.remote.repository.CategoryRepository

class GetCategoryUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(id: String): Category {
        return categoryRepository.getCategory(id)
    }
}