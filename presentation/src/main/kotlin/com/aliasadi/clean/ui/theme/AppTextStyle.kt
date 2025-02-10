import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class DefaultTitleStyle(fontSize: Int, lineHeight: Int){
    private val baseStyle = TextStyle(fontSize = fontSize.sp, lineHeight = lineHeight.sp)

    fun regular() = baseStyle.copy(fontWeight = FontWeight.Normal)
    fun medium() = baseStyle.copy(fontWeight = FontWeight.Medium)
    fun semiBold() = baseStyle.copy(fontWeight = FontWeight.SemiBold)
}

object AppTextStyle {
    val title1 = DefaultTitleStyle(24, 28)
    val title2 = DefaultTitleStyle(20, 26)
    val body2 = DefaultTitleStyle(14,18)
}
