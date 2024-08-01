package bob13.domain.repository

import org.springframework.stereotype.Repository

@Repository
class KeypadRepository {
    private val keys = mutableMapOf<String, String>()

    fun saveKey(id: String, key: String) {
        keys[id] = key
    }

    fun getKey(id: String): String? {
        return keys[id]
    }
}
