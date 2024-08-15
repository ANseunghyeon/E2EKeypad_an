package bob13.presentation.controller

import bob13.domain.model.Keypad
import bob13.domain.service.KeypadService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = ["http://localhost:3000"])
class KeypadController(
    private val keypadService: KeypadService
) {

    @GetMapping("/keypad")
    fun getKeypads(@RequestParam("id") id: String): Keypad {
        return keypadService.saveKey(id)
    }
}
