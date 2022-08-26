package vegabobo.dsusideloader.ui.components

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScreen(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(0.dp),
    columnContent: Boolean = true,
    enableDefaultScrollBehavior: Boolean = true,
    topBar: @Composable (TopAppBarScrollBehavior) -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    outsideContent: @Composable (PaddingValues) -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec,
        rememberTopAppBarScrollState()
    )

    val scrollBehaviorModifier =
        if (enableDefaultScrollBehavior) Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) else Modifier

    val insets = WindowInsets
        .systemBars
        .only(WindowInsetsSides.Vertical)
        .asPaddingValues()

    Surface(
        modifier = Modifier.padding(insets)
    ) {
        Scaffold(
            modifier = scrollBehaviorModifier
                .fillMaxSize(),
            topBar = { topBar(scrollBehavior) },
            bottomBar = { bottomBar() },
            content = { innerPadding ->
                val scrollModifier =
                    if (enableDefaultScrollBehavior) Modifier.verticalScroll(rememberScrollState()) else Modifier
                if (columnContent)
                    Column(
                        modifier = modifier
                            .padding(innerPadding)
                            .then(scrollModifier),
                        verticalArrangement = verticalArrangement,
                    ) {
                        content()
                    }
                else
                    Surface(modifier = modifier.padding(innerPadding)) {
                        content()
                    }
            }
        )
    }

    outsideContent(insets)

}

