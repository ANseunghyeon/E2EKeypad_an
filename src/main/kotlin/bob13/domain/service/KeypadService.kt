package bob13.domain.service

import bob13.domain.model.Keypad
import org.springframework.stereotype.Service

@Service
class KeypadService {

    private val keypads = mutableMapOf<Long, Keypad>()

    fun getKeypads(): List<Keypad> {
        return keypads.values.toList()
    }

    fun putKeypadKey(id: Long, userId: String, key: String, images: List<String>) {
        val shuffledImages = images.shuffled()
        val keypad = Keypad(id, userId, key, shuffledImages)
        keypads[id] = keypad
    }

    fun getKeypadImages(id: Long): List<String> {
        return keypads[id]?.images ?: emptyList()
    }
}
