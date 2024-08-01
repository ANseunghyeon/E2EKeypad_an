package bob13.domain.service

import bob13.domain.model.Keypad
import bob13.domain.repository.KeypadRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class KeypadService(
    private val keypadRepository: KeypadRepository
) {

    fun getKeypads(): Keypad {
        // 리소스에서 이미지를 로드하고 base64로 인코딩
        val images = (1..9).map { "image$it" } // 이미지 로딩 및 인코딩 부분을 대체
        val shuffledImages = images.shuffled()
        return Keypad(UUID.randomUUID().toString(), shuffledImages)
    }

    fun saveKey(id: String, key: String) {
        keypadRepository.saveKey(id, key)
    }

    fun validateKeypad(id: String, input: List<String>): Boolean {
        val key = keypadRepository.getKey(id) ?: return false
        // 키를 이용해 입력된 값을 검증하는 로직 구현
        return true // 검증 로직을 대체
    }
}
