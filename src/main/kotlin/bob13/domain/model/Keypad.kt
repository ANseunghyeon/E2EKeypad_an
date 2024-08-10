package bob13.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("Keypad")
data class Keypad(
    @Id val id: String,
    val base64Images: String,
    val hash: List<String?>
)
