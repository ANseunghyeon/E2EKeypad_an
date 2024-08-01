package bob13.presentation.controller

import bob13.domain.service.KeypadService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/keypad")
class KeypadController(
    private val keypadService: KeypadService
) {

    @GetMapping("/{id}/images")
    @ResponseBody
    fun getKeypadImages(@PathVariable id: Long): List<String> {
        return keypadService.getKeypadImages(id)
    }

    @PostMapping("/{id}")
    @ResponseBody
    fun putKeypadKey(
        @PathVariable id: Long,
        @RequestParam userId: String,
        @RequestParam key: String,
        @RequestParam images: List<String>
    ) {
        keypadService.putKeypadKey(id, userId, key, images)
    }
}
