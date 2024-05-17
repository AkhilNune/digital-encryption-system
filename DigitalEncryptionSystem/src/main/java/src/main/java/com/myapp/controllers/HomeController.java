package src.main.java.com.myapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/encrypt")
    public String encryptDecryptPage() {
        return "encryptdecrypt";
    }

    @GetMapping("/sender")
    public String senderPage() {
        return "sender";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

//    @GetMapping("/receiver")
//    public String receiverPage() {
//        return "receiver";
//    }
}
