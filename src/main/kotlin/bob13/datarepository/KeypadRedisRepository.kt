package bob13.domain.repository

import bob13.domain.model.Keypad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KeypadRedisRepository : CrudRepository<Keypad, String> {

}