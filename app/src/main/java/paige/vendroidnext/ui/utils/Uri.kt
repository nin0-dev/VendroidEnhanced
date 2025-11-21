package paige.vendroidnext.ui.utils

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openUrl(
	context: Context,
	url: String
) {
	context.startActivity(
		Intent()
			.setAction(Intent.ACTION_VIEW)
			.setData(url.toUri())
	)
}
