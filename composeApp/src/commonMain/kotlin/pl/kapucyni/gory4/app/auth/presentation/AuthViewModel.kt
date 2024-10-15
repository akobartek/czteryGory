package pl.kapucyni.gory4.app.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kapucyni.gory4.app.auth.domain.AuthRepository
import pl.kapucyni.gory4.app.auth.domain.EmailNotVerifiedException
import pl.kapucyni.gory4.app.common.data.PreferencesRepository
import pl.kapucyni.gory4.app.common.presentation.SnackbarEvent
import pl.kapucyni.gory4.app.common.utils.isValidEmail

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthScreenState())
    val authState: StateFlow<AuthScreenState> = _authState.asStateFlow()

    private val _snackbarAuthState = MutableStateFlow<SnackbarEvent?>(null)
    val snackbarAuthState: StateFlow<SnackbarEvent?> = _snackbarAuthState.asStateFlow()

    init {
        viewModelScope.launch { updateEmail(preferencesRepository.getLastUsedEmail()) }
    }

    fun updateEmail(value: String) {
        _authState.update { it.copy(email = value) }
    }

    fun updatePassword(value: String) {
        _authState.update { it.copy(password = value) }
    }

    fun updatePasswordHidden() {
        _authState.update { it.copy(passwordHidden = !it.passwordHidden) }
    }

    fun hideNoInternetDialog() {
        _authState.update { it.copy(noInternetAction = null) }
    }

    fun toggleForgottenPasswordDialogVisibility() {
        _authState.update {
            it.copy(
                forgottenPasswordDialogVisible = !it.forgottenPasswordDialogVisible,
                forgottenPasswordDialogError = false
            )
        }
    }

    fun toggleSignUpSuccessVisibility() {
        _authState.update { it.copy(isSignedUpDialogVisible = !it.isSignedUpDialogVisible) }
    }

    fun toggleEmailUnverifiedDialogVisibility(resend: Boolean) {
        _authState.update { it.copy(emailUnverifiedDialogVisible = !it.emailUnverifiedDialogVisible) }
        viewModelScope.launch {
            if (resend) authRepository.sendVerificationEmail()
            authRepository.signOut()
        }
    }

    fun signIn() {
        if (!validateInput(false)) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signIn(authState.value.email, authState.value.password)
            _authState.update {
                if (result.isSuccess && result.getOrDefault(false)) {
                    preferencesRepository.updateLastUsedEmail(authState.value.email)
                    _snackbarAuthState.update { SnackbarEvent.SIGNED_IN }
                    it.copy(isSignedIn = true)
                } else {
                    _snackbarAuthState.update { SnackbarEvent.SIGN_IN_ERROR }
                    when (result.exceptionOrNull()) {
                        is EmailNotVerifiedException ->
                            it.copy(emailUnverifiedDialogVisible = true)

                        is FirebaseNetworkException ->
                            it.copy(noInternetAction = NoInternetAction.SIGN_IN)

                        else ->
                            it.copy(
                                emailError = EmailErrorType.OTHER,
                                passwordError = PasswordErrorType.INVALID
                            )
                    }
                }
            }
        }
    }

    fun signUp() {
        if (!validateInput(true)) return

        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signUp(authState.value.email, authState.value.password)
            _authState.update {
                if (result.isSuccess && result.getOrDefault(false))
                    it.copy(isSignedUpDialogVisible = true)
                else {
                    when (result.exceptionOrNull()) {
                        is FirebaseAuthUserCollisionException ->
                            it.copy(emailError = EmailErrorType.USER_EXISTS)

                        is FirebaseNetworkException ->
                            it.copy(noInternetAction = NoInternetAction.SIGN_UP)

                        else -> {
                            _snackbarAuthState.update { SnackbarEvent.SIGN_UP_ERROR }
                            it
                        }
                    }
                }
            }
        }
    }

    fun sendResetPasswordEmail(email: String) {
        if (!email.isValidEmail()) {
            _authState.update { it.copy(forgottenPasswordDialogError = true) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.sendRecoveryEmail(email)
            _authState.update {
                if (result.isSuccess && result.getOrDefault(false)) {
                    _snackbarAuthState.update { SnackbarEvent.FORGOTTEN_PASSWORD_MESSAGE_SENT }
                    it.copy(forgottenPasswordDialogVisible = false)
                } else {
                    when (result.exceptionOrNull()) {
                        is FirebaseNetworkException -> it.copy(
                            noInternetAction = NoInternetAction.RESET_PASSWORD,
                            forgottenPasswordDialogVisible = false
                        )

                        else -> it.copy(forgottenPasswordDialogError = true)
                    }
                }
            }
        }
    }

    private fun validateInput(isSigningUp: Boolean): Boolean {
        val newState = _authState.value.let { state ->
            state.copy(
                emailError = if (state.email.isValidEmail()) null else EmailErrorType.INVALID,
                passwordError = when {
                    (!isSigningUp && state.password.isBlank()) -> PasswordErrorType.EMPTY
                    (isSigningUp && !state.password.isValidPassword()) -> PasswordErrorType.WRONG
                    else -> null
                }
            )
        }
        _authState.value = newState
        return newState.emailError == null && newState.passwordError == null
    }

    private fun CharSequence.isValidPassword(): Boolean {
        if (this.length < 8) return false

        val passwordRegex = Regex("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{8,20})")
        return this.matches(passwordRegex)
    }
}