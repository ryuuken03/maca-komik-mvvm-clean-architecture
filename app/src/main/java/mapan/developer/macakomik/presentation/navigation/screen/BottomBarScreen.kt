package mapan.developer.macakomik.presentation.navigation.screen
import mapan.developer.macakomik.R

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
sealed class BottomBarScreen(val route: String) {
    object Home : BottomBar(
        route = "home",
        titleResId = R.string.menu_home,
        icon = R.drawable.baseline_whatshot_24,
        iconFocused = R.drawable.baseline_whatshot_24
    )

    object History : BottomBar(
        route = "history",
        titleResId = R.string.menu_history,
        icon = R.drawable.baseline_history_24,
        iconFocused = R.drawable.baseline_history_24
    )

    object Bookmarks : BottomBar(
        route = "bookmarks",
        titleResId = R.string.menu_bookmarks,
        icon = R.drawable.baseline_bookmarks_24,
        iconFocused = R.drawable.baseline_bookmarks_24
    )

    object Setting : BottomBar(
        route = "setting",
        titleResId = R.string.menu_setting,
        icon = R.drawable.baseline_settings_24,
        iconFocused = R.drawable.baseline_settings_24
    )
}