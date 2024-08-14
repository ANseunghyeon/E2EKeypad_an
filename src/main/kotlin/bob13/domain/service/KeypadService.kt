package bob13.domain.service

import bob13.domain.model.Keypad
import bob13.domain.repository.KeypadRedisRepository
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.security.MessageDigest
import java.util.*
import javax.imageio.ImageIO

@Service
class KeypadService(
    private val keypadRedisRepository: KeypadRedisRepository
) {

    private fun generateHash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
    fun generateAndMapHashes(): List<String> {
        val hashList = mutableListOf<String>()

        for (i in 0..9) {
            val randomUUID = UUID.randomUUID().toString()
            val hashValue = generateHash(randomUUID)
            hashList.add(hashValue)
        }

        return hashList
    }


    fun generateKeypadImageWithBase64(imageDir: String, hashes: List<String>): Keypad {
        val keypadLayout = mutableListOf<String?>()


        val positions = (0..11).shuffled()
        for (i in positions) {
            if (i < 10) {
                keypadLayout.add(hashes[i])
            } else {
                keypadLayout.add(null)
            }
        }

        val keypadImage = BufferedImage(160, 150, BufferedImage.TYPE_INT_RGB)
        val g: Graphics2D = keypadImage.createGraphics()
        g.color = Color.WHITE
        g.fillRect(0, 0, 160, 150)

        for (i in 0 until 12) {
            val x = (i % 4) * 25
            val y = (i / 4) * 25
            if (positions[i] < 10) {

                val resourcePath = "/images/_${positions[i]}.png"
                val img: BufferedImage? = javaClass.getResourceAsStream(resourcePath)?.use { stream: InputStream ->
                    ImageIO.read(stream)
                }
                img?.let {
                    g.drawImage(it, x, y, null)
                } ?: println("Image not found for position $i: $resourcePath")
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