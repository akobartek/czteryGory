package pl.kapucyni.gory4.app.directors.presentation
import pl.kapucyni.gory4.app.common.presentation.ListViewModel
import pl.kapucyni.gory4.app.directors.domain.model.Director
import pl.kapucyni.gory4.app.directors.domain.usecases.FilterDirectorsUseCase
import pl.kapucyni.gory4.app.directors.domain.usecases.GetDirectorsUseCase

class DirectorsListViewModel(
    getDirectorsUseCase: GetDirectorsUseCase,
    filterDirectorsUseCase: FilterDirectorsUseCase,
) : ListViewModel<Director>(getDirectorsUseCase, filterDirectorsUseCase)