package bob13.domain.model

data class Keypad(
    val id: Long,
    val userId: String,
    val key: String,
    val images: List<String>
)
