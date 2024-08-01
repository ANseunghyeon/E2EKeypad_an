package bob13.presentation.controller

import bob13.domain.service.KeypadService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/keypad")
class KeypadController(
    private val keypadService: KeypadService){


    @GetMapping
    fun getKeypads() {

    }

    @PostMapping("/{id}")
    fun putKeypad_key() {

    }

}
