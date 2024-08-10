package bob13.domain.repository

import bob13.domain.model.Keypad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KeypadRedisRepository : CrudRepository<Keypad, String> {
    // 필요한 경우 추가적인 쿼리 메서드를 정의할 수 있습니다.
}
