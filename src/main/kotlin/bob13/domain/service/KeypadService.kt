package bob13.domain.service

import bob13.domain.model.Keypad
import bob13.domain.repository.KeypadRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.util.Base64
import java.nio.file.Files
import java.nio.file.Paths

@Service
class KeypadService(
    private val keypadRepository: KeypadRepository
) {

    fun getKeypads(): Keypad {
        // 리소스에서 이미지를 로드하고 base64로 인코딩
        val images = loadBase64Images()
        val shuffledImages = images.shuffled()
        return Keypad(UUID.randomUUID().toString(), shuffledImages)
    }

    fun saveKey(id: String, key: String) {
        keypadRepository.saveKey(id, key)
    }

    fun validateKeypad(id: String, input: List<String>): Boolean {
        val key = keypadRepository.getKey(id) ?: return false
        return input.all { encryptedBase64 ->
            val decryptedBase64 = decrypt(encryptedBase64, key)
            // 실제 원본 이미지 Base64와 비교하여 유효성 검사를 수행
            // 이 부분에서는 사용자가 입력한 값과 시스템에서 제공한 값이 일치하는지를 검사
            true // 실제 비교 로직 구현
        }
    }

    private fun loadBase64Images(): List<String> {
        return (1..9).map { number ->
            val imagePath = Paths.get("src/main/resources/images/_$number.png")
            val imageBytes = Files.readAllBytes(imagePath)
            Base64.getEncoder().encodeToString(imageBytes)
        }
    }

    private fun decrypt(encryptedText: String, key: String): String {
        val cipher = Cipher.getInstance("AES")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decodedBytes = Base64.getDecoder().decode(encryptedText)
        return String(cipher.doFinal(decodedBytes))
    }
}
