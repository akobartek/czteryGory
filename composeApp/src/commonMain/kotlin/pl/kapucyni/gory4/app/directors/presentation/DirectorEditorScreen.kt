package pl.kapucyni.gory4.app.directors.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.director_add
import czterygory.composeapp.generated.resources.director_city
import czterygory.composeapp.generated.resources.director_country
import czterygory.composeapp.generated.resources.director_description
import czterygory.composeapp.generated.resources.director_edit
import czterygory.composeapp.generated.resources.director_email
import czterygory.composeapp.generated.resources.director_name
import czterygory.composeapp.generated.resources.director_phone
import czterygory.composeapp.generated.resources.director_region
import czterygory.composeapp.generated.resources.email_error_invalid
import czterygory.composeapp.generated.resources.phone_error_invalid
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pl.kapucyni.gory4.app.common.presentation.composables.HeightSpacer
import pl.kapucyni.gory4.app.common.presentation.composables.LoadingBox
import pl.kapucyni.gory4.app.common.presentation.composables.WidthSpacer
import pl.kapucyni.gory4.app.directors.presentation.composables.DirectorEditorPlacesLeft
import pl.kapucyni.gory4.app.directors.presentation.composables.DirectorEditorTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectorEditorScreen(
    navigateUp: () -> Unit,
    directorId: String?,
    isAdmin: Boolean,
    viewModel: DirectorEditorViewModel = koinInject(),
) {
    val focusManager = LocalFocusManager.current
    val currentDirector by viewModel.director.collectAsStateWithLifecycle()
    val errors by viewModel.editorErrors.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (currentDirector == null)
            viewModel.init(directorId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(directorId?.let { Res.string.director_edit }
                        ?: Res.string.director_add))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    if (isAdmin && directorId != null) {
                        IconButton(onClick = {
                            if (viewModel.deleteDirector()) {
                                focusManager.clearFocus()
                                navigateUp()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                            )
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (viewModel.saveDirector()) {
                    focusManager.clearFocus()
                    navigateUp()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                )
            }
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            currentDirector?.let { director ->
                DirectorEditorTextField(
                    value = director.name,
                    onValueChange = viewModel::changeName,
                    labelRes = Res.string.director_name,
                    isError = errors.nameError,
                )
                HeightSpacer(12.dp)
                DirectorEditorTextField(
                    value = director.city,
                    onValueChange = viewModel::changeCity,
                    labelRes = Res.string.director_city,
                    isError = errors.cityError,
                )
                HeightSpacer(12.dp)
                Row {
                    DirectorEditorTextField(
                        value = director.region,
                        onValueChange = viewModel::changeRegion,
                        labelRes = Res.string.director_region,
                        isError = errors.regionError,
                        modifier = Modifier.weight(1f),
                    )
                    WidthSpacer(4.dp)
                    DirectorEditorTextField(
                        value = director.country,
                        onValueChange = viewModel::changeCountry,
                        labelRes = Res.string.director_country,
                        isError = errors.countryError,
                        modifier = Modifier.weight(1f),
                    )
                }
                HeightSpacer(12.dp)
                DirectorEditorPlacesLeft(
                    placesLeft = director.placesLeft,
                    onValueChange = viewModel::changePlacesLeft
                )
                HeightSpacer(12.dp)
                DirectorEditorTextField(
                    value = director.emailAddress,
                    onValueChange = viewModel::changeEmail,
                    labelRes = Res.string.director_email,
                    isError = errors.emailError,
                    errorMessageRes = Res.string.email_error_invalid,
                    keyboardType = KeyboardType.Email,
                )
                HeightSpacer(12.dp)
                Row(verticalAlignment = Alignment.Bottom) {
                    OutlinedTextField(
                        value = director.phoneCountryCode,
                        onValueChange = viewModel::changePhoneCountryCode,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                        ),
                        singleLine = true,
                        modifier = Modifier.width(70.dp),
                    )
                    DirectorEditorTextField(
                        value = director.phoneNumber,
                        onValueChange = viewModel::changePhone,
                        labelRes = Res.string.director_phone,
                        isError = errors.phoneError,
                        errorMessageRes = Res.string.phone_error_invalid,
                        keyboardType = KeyboardType.Phone,
                        modifier = Modifier.weight(1f)
                    )
                }
                HeightSpacer(12.dp)
                DirectorEditorTextField(
                    value = director.description,
                    onValueChange = viewModel::changeDescription,
                    labelRes = Res.string.director_description,
                    isError = errors.descriptionError,
                    capitalization = KeyboardCapitalization.Sentences,
                    maxLines = 6,
                )
                HeightSpacer(40.dp)
            } ?: LoadingBox()
        }
    }
}