package bob13.presentation.controller

import bob13.domain.model.Keypad
import bob13.domain.service.KeypadService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/keypad")
class KeypadController(
    private val keypadService: KeypadService
) {

    @GetMapping
    fun getKeypads(): Keypad {
        return keypadService.getKeypads()
    }

    @PostMapping("/{id}")
    fun putKeypadKey(@PathVariable id: String, @RequestBody key: String) {
        keypadService.saveKey(id, key)
    }

    @PostMapping("/validate/{id}")
    fun validateKeypad(
        @PathVariable id: String,
        @RequestBody input: List<String>
    ): Boolean {
        return keypadService.validateKeypad(id, input)
    }
}
