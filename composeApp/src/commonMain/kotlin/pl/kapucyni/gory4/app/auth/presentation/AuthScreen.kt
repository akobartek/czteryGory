package pl.kapucyni.gory4.app.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.cancel
import czterygory.composeapp.generated.resources.cd_clear_field
import czterygory.composeapp.generated.resources.email
import czterygory.composeapp.generated.resources.email_error_invalid
import czterygory.composeapp.generated.resources.email_error_user_exists
import czterygory.composeapp.generated.resources.empty_field_error
import czterygory.composeapp.generated.resources.forgot_password
import czterygory.composeapp.generated.resources.hide_password
import czterygory.composeapp.generated.resources.ok
import czterygory.composeapp.generated.resources.password
import czterygory.composeapp.generated.resources.password_error_empty
import czterygory.composeapp.generated.resources.password_error_wrong
import czterygory.composeapp.generated.resources.show_password
import czterygory.composeapp.generated.resources.sign_in
import czterygory.composeapp.generated.resources.sign_in_error
import czterygory.composeapp.generated.resources.sign_up
import czterygory.composeapp.generated.resources.sign_up_successful_dialog_message
import czterygory.composeapp.generated.resources.sign_up_successful_dialog_title
import czterygory.composeapp.generated.resources.verify_email_dialog_message
import czterygory.composeapp.generated.resources.verify_email_dialog_title
import czterygory.composeapp.generated.resources.verify_email_send_again
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pl.kapucyni.gory4.app.auth.presentation.composables.ResetPasswordDialog
import pl.kapucyni.gory4.app.common.presentation.SnackbarEvent
import pl.kapucyni.gory4.app.common.presentation.composables.AppAlertDialog
import pl.kapucyni.gory4.app.common.presentation.composables.AppLogo
import pl.kapucyni.gory4.app.common.presentation.composables.HeightSpacer
import pl.kapucyni.gory4.app.common.presentation.composables.NoInternetDialog

@Composable
fun AuthScreen(
    showSnackbar: (SnackbarEvent) -> Unit,
    navigateHome: () -> Unit,
    viewModel: AuthViewModel = koinInject(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val (emailRef, passwordRef) = remember { FocusRequester.createRefs() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val state by viewModel.authState.collectAsStateWithLifecycle()
    val snackbarState by viewModel.snackbarAuthState.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state.isSignedIn) navigateHome()
    }

    LaunchedEffect(snackbarState) {
        snackbarState?.let { snackbarEvent ->
            showSnackbar(snackbarEvent)
        }
    }

    Scaffold(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { focusManager.clearFocus(true) }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 40.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AppLogo(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { focusManager.clearFocus(true) }
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::updateEmail,
                singleLine = true,
                label = { Text(text = stringResource(Res.string.email)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                trailingIcon = {
                    if (state.email.isNotBlank())
                        IconButton(onClick = { viewModel.updateEmail("") }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(Res.string.cd_clear_field)
                            )
                        }
                },
                isError = state.emailError != null,
                supportingText = if (state.emailError != null && state.emailError != EmailErrorType.OTHER) {
                    {
                        Text(
                            text = stringResource(
                                when (state.emailError) {
                                    EmailErrorType.INVALID -> Res.string.email_error_invalid
                                    EmailErrorType.USER_EXISTS -> Res.string.email_error_user_exists
                                    else -> Res.string.empty_field_error
                                }
                            )
                        )
                    }
                } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailRef)
                    .focusProperties { next = passwordRef }
            )
            HeightSpacer(4.dp)
            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::updatePassword,
                singleLine = true,
                label = { Text(text = stringResource(Res.string.password)) },
                visualTransformation = if (state.passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                trailingIcon = {
                    IconButton(onClick = viewModel::updatePasswordHidden) {
                        if (state.passwordHidden)
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = stringResource(Res.string.show_password)
                            )
                        else
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(Res.string.hide_password)
                            )
                    }
                },
                isError = state.passwordError != null,
                supportingText = if (state.passwordError != null) {
                    {
                        Text(
                            text = stringResource(
                                when (state.passwordError) {
                                    PasswordErrorType.EMPTY -> Res.string.password_error_empty
                                    PasswordErrorType.WRONG -> Res.string.password_error_wrong
                                    PasswordErrorType.INVALID -> Res.string.sign_in_error
                                    else -> Res.string.empty_field_error
                                }
                            )
                        )
                    }
                } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordRef)
            )
            HeightSpacer(8.dp)
            Button(
                onClick = {
                    focusManager.clearFocus(true)
                    viewModel.signIn()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(Res.string.sign_in))
            }
            HeightSpacer(8.dp)
            OutlinedButton(
                onClick = {
                    focusManager.clearFocus(true)
                    viewModel.signUp()
                },
                modifier = Modifier.fillMaxWidth(0.75f)
            ) {
                Text(text = stringResource(Res.string.sign_up))
            }
            HeightSpacer(8.dp)
            OutlinedButton(
                onClick = viewModel::toggleForgottenPasswordDialogVisibility,
                modifier = Modifier.fillMaxWidth(0.75f)
            ) {
                Text(text = stringResource(Res.string.forgot_password))
            }
        }

        if (state.forgottenPasswordDialogVisible)
            ResetPasswordDialog(
                onReset = viewModel::sendResetPasswordEmail,
                onCancel = viewModel::toggleForgottenPasswordDialogVisibility,
                isError = state.forgottenPasswordDialogError
            )

        NoInternetDialog(
            isVisible = state.noInternetAction != null,
            onReconnect = {
                viewModel.hideNoInternetDialog()
                when (state.noInternetAction) {
                    NoInternetAction.SIGN_IN -> viewModel.signIn()
                    NoInternetAction.SIGN_UP -> viewModel.signUp()
                    NoInternetAction.RESET_PASSWORD -> viewModel.toggleForgottenPasswordDialogVisibility()
                    else -> {}
                }
            },
            onDismiss = viewModel::hideNoInternetDialog
        )

        AppAlertDialog(
            isVisible = state.isSignedUpDialogVisible,
            imageVector = Icons.Outlined.ManageAccounts,
            dialogTitleId = Res.string.sign_up_successful_dialog_title,
            dialogTextId = Res.string.sign_up_successful_dialog_message,
            confirmBtnTextId = Res.string.ok,
            onConfirm = viewModel::toggleSignUpSuccessVisibility,
            dismissible = false,
        )

        AppAlertDialog(
            isVisible = state.emailUnverifiedDialogVisible,
            imageVector = Icons.Outlined.ErrorOutline,
            dialogTitleId = Res.string.verify_email_dialog_title,
            dialogTextId = Res.string.verify_email_dialog_message,
            confirmBtnTextId = Res.string.verify_email_send_again,
            onConfirm = { viewModel.toggleEmailUnverifiedDialogVisibility(true) },
            dismissible = false,
            dismissBtnTextId = Res.string.cancel,
            onDismissRequest = { viewModel.toggleEmailUnverifiedDialogVisibility(false) }
        )
    }
}