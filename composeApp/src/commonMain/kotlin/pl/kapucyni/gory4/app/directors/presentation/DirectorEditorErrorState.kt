package pl.kapucyni.gory4.app.directors.presentation

data class DirectorEditorErrorState(
    val nameError: Boolean = false,
    val cityError: Boolean = false,
    val regionError: Boolean = false,
    val countryError: Boolean = false,
    val descriptionError: Boolean = false,
    val emailError: Boolean = false,
    val phoneError: Boolean = false,
) {
    fun isInputValid() =
        !nameError && !cityError && !regionError && !countryError
                && !descriptionError && !emailError && !phoneError
}