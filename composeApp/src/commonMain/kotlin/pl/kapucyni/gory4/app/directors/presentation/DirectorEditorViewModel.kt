package pl.kapucyni.gory4.app.directors.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kapucyni.gory4.app.common.utils.isValidEmail
import pl.kapucyni.gory4.app.common.utils.isValidPhoneNumber
import pl.kapucyni.gory4.app.directors.domain.model.Director
import pl.kapucyni.gory4.app.directors.domain.usecases.GetDirectorByIdUseCase
import pl.kapucyni.gory4.app.directors.domain.usecases.SaveDirectorUseCase

class DirectorEditorViewModel(
    private val getDirectorByIdUseCase: GetDirectorByIdUseCase,
    private val saveDirectorUseCase: SaveDirectorUseCase,
) : ViewModel() {

    private val _director = MutableStateFlow<Director?>(null)
    val director = _director.asStateFlow()

    private val _editorErrors = MutableStateFlow(DirectorEditorErrorState())
    val editorErrors = _editorErrors.asStateFlow()

    fun init(directorUserId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _director.update {
                directorUserId?.let { id ->
                    getDirectorByIdUseCase(id)
                } ?: Director()
            }
        }
    }

    fun saveDirector(): Boolean {
        return director.value?.let { directorToSave ->
            if (validateInput(directorToSave).not()) return@let false

            viewModelScope.launch(Dispatchers.IO) {
                saveDirectorUseCase(directorToSave)
            }
            true
        } ?: false
    }

    fun changeName(newName: String) {
        _director.update { it?.copy(name = newName) }
    }

    fun changeCity(newCity: String) {
        _director.update { it?.copy(city = newCity) }
    }

    fun changeRegion(newRegion: String) {
        _director.update { it?.copy(region = newRegion) }
    }

    fun changeCountry(newCountry: String) {
        _director.update { it?.copy(country = newCountry) }
    }

    fun changeDescription(newDescription: String) {
        _director.update { it?.copy(description = newDescription) }
    }

    fun changeEmail(newEmail: String) {
        _director.update { it?.copy(emailAddress = newEmail) }
    }

    fun changePhoneCountryCode(newCode: String) {
        _director.update { it?.copy(phoneCountryCode = newCode) }
    }

    fun changePhone(newPhone: String) {
        _director.update { it?.copy(phoneNumber = newPhone) }
    }

    fun changePlacesLeft(newNumber: Int) {
        _director.update { it?.copy(placesLeft = newNumber) }
    }

    private fun validateInput(director: Director): Boolean =
        DirectorEditorErrorState(
            nameError = director.name.isBlank(),
            cityError = director.city.isBlank(),
            regionError = director.region.isBlank(),
            countryError = director.country.isBlank(),
            descriptionError = false,
            emailError = director.emailAddress.let { it.isNotBlank() && it.isValidEmail().not() },
            phoneError =
            director.phoneNumber.let { it.isNotBlank() && it.isValidPhoneNumber().not() },
        ).let { errorState ->
            _editorErrors.update { errorState }
            errorState.isInputValid()
        }
}