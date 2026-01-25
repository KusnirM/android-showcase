package mk.digital.androidshowcase.presentation.screen.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.SharedFlow
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.presentation.base.CollectNavEvents
import mk.digital.androidshowcase.presentation.base.NavEvent
import mk.digital.androidshowcase.presentation.base.NavRouter
import mk.digital.androidshowcase.presentation.base.Route
import mk.digital.androidshowcase.presentation.component.AppPasswordTextField
import mk.digital.androidshowcase.presentation.component.AppTextField
import mk.digital.androidshowcase.presentation.component.buttons.ContainedButton
import mk.digital.androidshowcase.presentation.component.image.AppIconNeutral80
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer4
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer8
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.labelLarge.TextButtonPrimary
import mk.digital.androidshowcase.presentation.component.text.titleLarge.TextTitleLargePrimary
import mk.digital.androidshowcase.presentation.foundation.AppTheme
import mk.digital.androidshowcase.presentation.foundation.space12
import mk.digital.androidshowcase.presentation.foundation.space2
import mk.digital.androidshowcase.presentation.foundation.space4
import mk.digital.androidshowcase.presentation.foundation.space6

@Composable
fun RegisterScreen(
    router: NavRouter<Route>,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterNavEvents(router, viewModel.navEvent)
    RegisterScreen(
        state = state,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onRegister = viewModel::register,
        onLogin = viewModel::toLogin
    )
}

@Composable
fun RegisterScreen(
    state: RegisterUiState = RegisterUiState(),
    onNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onRegister: () -> Unit = {},
    onLogin: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(space4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer8()
        TextTitleLargePrimary(stringResource(R.string.register_title))
        Spacer8()

        // Name field
        AppTextField(
            value = state.name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.register_name_label),
            placeholder = stringResource(R.string.register_name_placeholder),
            isError = state.nameError != null,
            supportingText = state.nameError?.let { error ->
                when (error) {
                    RegisterNameError.EMPTY -> stringResource(R.string.register_name_empty)
                    RegisterNameError.TOO_SHORT -> stringResource(R.string.register_name_short)
                }
            },
            leadingIcon = {
                AppIconNeutral80(imageVector = Icons.Filled.Person, contentDescription = null)
            },
            showClearButton = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(space2))

        // Email field
        AppTextField(
            value = state.email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.register_email_label),
            placeholder = stringResource(R.string.register_email_placeholder),
            isError = state.emailError != null,
            supportingText = state.emailError?.let { error ->
                when (error) {
                    RegisterEmailError.EMPTY -> stringResource(R.string.register_email_empty)
                    RegisterEmailError.INVALID_FORMAT -> stringResource(R.string.register_email_invalid)
                    RegisterEmailError.ALREADY_EXISTS -> stringResource(R.string.register_email_already_exists)
                }
            },
            leadingIcon = {
                AppIconNeutral80(imageVector = Icons.Filled.Email, contentDescription = null)
            },
            showClearButton = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(space2))

        // Password field
        AppPasswordTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.register_password_label),
            placeholder = stringResource(R.string.register_password_placeholder),
            isError = state.passwordError != null,
            supportingText = state.passwordError?.let { error ->
                when (error) {
                    RegisterPasswordError.EMPTY -> stringResource(R.string.register_password_empty)
                    RegisterPasswordError.TOO_SHORT -> stringResource(R.string.register_password_short)
                    RegisterPasswordError.WEAK -> stringResource(R.string.register_password_weak)
                }
            },
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(space2))

        // Confirm Password field
        AppPasswordTextField(
            value = state.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.register_confirm_password_label),
            placeholder = stringResource(R.string.register_confirm_password_placeholder),
            isError = state.confirmPasswordError != null,
            supportingText = state.confirmPasswordError?.let { error ->
                when (error) {
                    RegisterConfirmPasswordError.EMPTY -> stringResource(R.string.register_confirm_password_empty)
                    RegisterConfirmPasswordError.MISMATCH -> stringResource(R.string.register_confirm_password_mismatch)
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onRegister()
                }
            )
        )

        Spacer(modifier = Modifier.height(space6))

        // Register button
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(space12),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            ContainedButton(
                text = stringResource(R.string.register_button),
                onClick = {
                    focusManager.clearFocus()
                    onRegister()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer4()

        // Login link
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextBodyMediumNeutral80(stringResource(R.string.register_has_account))
            TextButton(onClick = onLogin) {
                TextButtonPrimary(stringResource(R.string.register_login))
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun RegisterNavEvents(
    router: NavRouter<Route>,
    navEvent: SharedFlow<NavEvent>
) {
    CollectNavEvents(navEventFlow = navEvent) { event ->
        when (event) {
            is RegisterNavEvent.ToHome -> router.navigateTo(
                Route.HomeSection.Home,
                popUpTo = Route.Register::class,
                inclusive = true
            )

            is RegisterNavEvent.ToLogin -> router.onBack()
        }
    }
}

@Preview
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RegisterScreenPreview(
    @PreviewParameter(RegisterScreenPreviewParams::class) state: RegisterUiState
) {
    AppTheme {
        RegisterScreen(state = state)
    }
}

internal class RegisterScreenPreviewParams : PreviewParameterProvider<RegisterUiState> {
    override val values = sequenceOf(
        RegisterUiState(),
        RegisterUiState(
            name = "John Doe",
            email = "john@example.com",
            password = "Test123!",
            confirmPassword = "Test123!"
        ),
        RegisterUiState(isLoading = true),
        RegisterUiState(
            nameError = RegisterNameError.TOO_SHORT,
            emailError = RegisterEmailError.INVALID_FORMAT
        )
    )
}
