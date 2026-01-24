package mk.digital.androidshowcase.presentation.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
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
import mk.digital.androidshowcase.presentation.component.biometric.BiometricView
import mk.digital.androidshowcase.presentation.component.buttons.ContainedButton
import mk.digital.androidshowcase.presentation.component.image.AppIconNeutral80
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer4
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer6
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer8
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodySmall.TextBodySmallNeutral80
import mk.digital.androidshowcase.presentation.component.text.labelLarge.TextButtonPrimary
import mk.digital.androidshowcase.presentation.component.text.titleLarge.TextTitleLargePrimary
import mk.digital.androidshowcase.presentation.foundation.appColorScheme
import mk.digital.androidshowcase.presentation.foundation.space2
import mk.digital.androidshowcase.presentation.foundation.space4

@Composable
fun LoginScreen(
    router: NavRouter<Route>,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(space4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Skip button at top right
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = viewModel::skip) {
                TextButtonPrimary(stringResource(R.string.login_skip))
            }
        }

        Spacer4()

        TextTitleLargePrimary(stringResource(R.string.login_title))

        Spacer8()

        // Email field
        AppTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.login_email_label),
            placeholder = stringResource(R.string.login_email_placeholder),
            isError = state.emailError != null,
            supportingText = state.emailError?.let { error ->
                when (error) {
                    EmailError.EMPTY -> stringResource(R.string.login_email_empty)
                    EmailError.INVALID_FORMAT -> stringResource(R.string.login_email_invalid)
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

        Spacer2()

        // Password field
        AppPasswordTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.login_password_label),
            placeholder = stringResource(R.string.login_password_placeholder),
            isError = state.passwordError != null,
            supportingText = state.passwordError?.let { error ->
                when (error) {
                    PasswordError.EMPTY -> stringResource(R.string.login_password_empty)
                    PasswordError.TOO_SHORT -> stringResource(R.string.login_password_short)
                    PasswordError.WEAK -> stringResource(R.string.login_password_weak)
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    viewModel.login()
                }
            )
        )

        Spacer6()

        // Login button
        ContainedButton(
            text = stringResource(R.string.login_button),
            onClick = {
                focusManager.clearFocus()
                viewModel.login()
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer4()

        // Register link
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextBodyMediumNeutral80(stringResource(R.string.login_no_account))
            TextButton(onClick = viewModel::toRegister) {
                TextButtonPrimary(stringResource(R.string.login_register))
            }
        }

        // Biometric login
        if (state.biometricsAvailable) {
            Spacer6()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space2)
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                TextBodyMediumNeutral80(stringResource(R.string.login_or_divider))
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Spacer4()

            if (state.biometricsLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                BiometricView(modifier = Modifier, onClick = viewModel::authenticateWithBiometrics)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Test account section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.appColorScheme.neutral20,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(space4)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextBodySmallNeutral80(stringResource(R.string.login_test_account_hint))

                Spacer2()

                TextBodyMediumNeutral80(LoginViewModel.TEST_EMAIL)
                TextBodyMediumNeutral80(LoginViewModel.TEST_PASSWORD)

                Spacer2()

                OutlinedButton(
                    onClick = viewModel::fillTestAccount
                ) {
                    TextButtonPrimary(stringResource(R.string.login_test_account_fill))
                }
            }
        }

        Spacer4()
    }

    LoginNavEvents(router, viewModel.navEvent)
}

@Composable
private fun LoginNavEvents(
    router: NavRouter<Route>,
    navEvent: SharedFlow<NavEvent>
) {
    CollectNavEvents(navEventFlow = navEvent) { event ->
        when (event) {
            is LoginNavEvent.ToHome -> router.replaceAll(Route.HomeSection.Home)
            is LoginNavEvent.ToRegister -> router.navigateTo(Route.Register)
        }
    }
}
