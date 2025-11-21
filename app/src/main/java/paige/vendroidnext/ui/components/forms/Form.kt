package paige.vendroidnext.ui.components.forms

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Form(
	contentPadding: PaddingValues,
	content: @Composable () -> Unit
) {
	LazyColumn(
		modifier = Modifier
			.fillMaxWidth(),
		userScrollEnabled = true,
		contentPadding = contentPadding
	) {
		item {
			content()
		}
	}
}
