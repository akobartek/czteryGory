package pl.kapucyni.gory4.app.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.delete_account
import czterygory.composeapp.generated.resources.list_of_directors
import czterygory.composeapp.generated.resources.list_of_users
import czterygory.composeapp.generated.resources.profile_edition
import czterygory.composeapp.generated.resources.sign_out
import czterygory.composeapp.generated.resources.user_wait_for_approval
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pl.kapucyni.gory4.app.common.domain.model.UserType
import pl.kapucyni.gory4.app.common.presentation.Screen
import pl.kapucyni.gory4.app.common.presentation.composables.AppLogo
import pl.kapucyni.gory4.app.common.presentation.composables.HeightSpacer
import pl.kapucyni.gory4.app.common.presentation.composables.LoadingBox
import pl.kapucyni.gory4.app.home.presentation.composables.DeleteAccountDialog

@Composable
fun HomeScreen(
    navigate: (Screen) -> Unit,
    viewModel: HomeViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var deleteAccountVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state is HomeScreenState.UserNotSignedIn)
            navigate(Screen.Auth)
    }

    Scaffold { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 48.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AppLogo(modifier = Modifier.padding(top = 8.dp))
            HeightSpacer(8.dp)

            when (state) {
                is HomeScreenState.Loading, HomeScreenState.UserNotSignedIn -> {
                    LoadingBox(modifier = Modifier.padding(top = 36.dp))
                }

                is HomeScreenState.UserSignedIn -> {
                    val signedInState = state as HomeScreenState.UserSignedIn
                    when (signedInState.userType) {
                        UserType.NONE -> {
                            Text(
                                text = stringResource(Res.string.user_wait_for_approval),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                            )
                        }

                        UserType.MEMBER -> {
                            HomeScreenButton(
                                stringRes = Res.string.list_of_directors,
                                action = { navigate(Screen.DirectorsList(false)) },
                            )
                        }

                        UserType.DIRECTOR -> {
                            HomeScreenButton(
                                stringRes = Res.string.profile_edition,
                                action = { navigate(Screen.DirectorEditor(signedInState.userId)) },
                            )
                        }

                        UserType.ADMIN -> {
                            HomeScreenButton(
                                stringRes = Res.string.list_of_users,
                                action = { navigate(Screen.UsersList) },
                            )
                            HeightSpacer(4.dp)
                            HomeScreenButton(
                                stringRes = Res.string.list_of_directors,
                                action = { navigate(Screen.DirectorsList(true)) },
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = viewModel::signOut,
                        modifier = Modifier
                            .defaultMinSize(minWidth = 120.dp)
                            .padding(top = 8.dp),
                    ) {
                        Text(stringResource(Res.string.sign_out))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    OutlinedButton(
                        onClick = { deleteAccountVisible = true },
                        modifier = Modifier
                            .defaultMinSize(minWidth = 120.dp)
                            .padding(top = 8.dp),
                    ) {
                        Text(stringResource(Res.string.delete_account))
                    }
                }
            }

            DeleteAccountDialog(
                isVisible = deleteAccountVisible,
                onDelete = viewModel::deleteAccount,
                onDismiss = { deleteAccountVisible = false }
            )
        }
    }
}

@Composable
private fun HomeScreenButton(
    stringRes: StringResource,
    action: () -> Unit
) {
    Button(
        onClick = action,
        modifier = Modifier.defaultMinSize(minWidth = 180.dp)
    ) {
        Text(stringResource(stringRes))
    }
}