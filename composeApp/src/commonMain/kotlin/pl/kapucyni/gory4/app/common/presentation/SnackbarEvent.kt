package pl.kapucyni.gory4.app.common.presentation

import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.message_sent
import czterygory.composeapp.generated.resources.sign_up_error
import czterygory.composeapp.generated.resources.signed_in
import org.jetbrains.compose.resources.StringResource

enum class SnackbarEvent(val res: StringResource) {
    SIGNED_IN(Res.string.signed_in),
    SIGN_UP_ERROR(Res.string.sign_up_error),
    FORGOTTEN_PASSWORD_MESSAGE_SENT(Res.string.message_sent),
}