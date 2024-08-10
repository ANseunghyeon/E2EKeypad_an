package bob13.domain.service

import bob13.domain.model.Keypad
import bob13.domain.repository.KeypadRedisRepository
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.security.MessageDigest
import java.util.*
import javax.imageio.ImageIO

@Service
class KeypadService(
    private val keypadRedisRepository: KeypadRedisRepository
) {
    // SHA-256 해시값을 생성하는 함수
    private fun generateHash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    // 10개의 랜덤 해시값을 생성하고 이를 0~9의 키에 맵핑하는 함수
    fun generateAndMapHashes(): List<String> {
        val hashList = mutableListOf<String>()

        for (i in 0..9) {
            val randomUUID = UUID.randomUUID().toString()
            val hashValue = generateHash(randomUUID)
            hashList.add(hashValue)
        }

        return hashList
    }

    // 전체 키패드 이미지를 생성하고 이를 base64로 인코딩하는 함수
    fun generateKeypadImageWithBase64(imageDir: String, hashes: List<String>): Keypad {
        val keypadLayout = mutableListOf<String?>()

        // 4x3의 키패드 레이아웃에 해시값을 랜덤하게 배치
        val positions = (0..11).shuffled()
        for (i in positions) {
            if (i < 10) {
                keypadLayout.add(hashes[i])
            } else {
                keypadLayout.add(null)
            }
        }

        // 각 숫자 이미지를 합쳐서 하나의 키패드 이미지를 생성
        val keypadImage = BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB)
        val g: Graphics2D = keypadImage.createGraphics()
        g.color = Color.WHITE
        g.fillRect(0, 0, 400, 300) // 백그라운드를 하얀색으로

        for (i in 0 until 12) {
            val x = (i % 4) * 100
            val y = (i / 4) * 100
            if (positions[i] < 10) {
                val img = ImageIO.read(File("$imageDir/${positions[i]}_0.png"))
                g.drawImage(img, x, y, null)
            }
        }
        g.dispose()

        // 최종 합성된 이미지를 base64로 인코딩
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(keypadImage, "png", outputStream)
        val base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray())

        // 키패드를 포함한 Keypad 객체 생성
        return Keypad(
            id = UUID.randomUUID().toString(),  // 새 키패드 ID 생성
            base64Images = base64Image,
            hash = keypadLayout
        )
    }

    fun saveKey(id: String): Keypad {
        val hashes = generateAndMapHashes()
        val keypad = generateKeypadImageWithBase64("resources/images", hashes)
        // Redis에 저장
        keypadRedisRepository.save(keypad)
        return keypad
    }

    fun getKeypads(): Keypad {
        // Redis에서 저장된 키패드를 가져오는 로직 (예시)
        return keypadRedisRepository.findAll().first()
    }
}
