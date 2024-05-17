package src.main.java.com.myapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

@Controller
public class EncryptionController {

    private SecretKey secretKey;

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam("text") String text, Model model) {
        try {
            if (secretKey == null) {
                // Generate the secret key if it's null
                generateKey(model);
                if (secretKey == null) {
                    // Key generation failed, return an error
                    model.addAttribute("error", "Key generation failed.");
                    return "index";
                }
            }

            // Wrap the secret key using SecretKeySpec
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

            // Initialize the Cipher object with the wrapped secret key
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            // Perform encryption
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            model.addAttribute("encryptedText", encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Encryption failed.");
        }
        return "encryptdecrypt";
    }

//    @PostMapping("/decrypt")
//    public String decrypt(@RequestParam("encryptedText") String encryptedText, Model model) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
//            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//            String decryptedText = new String(decryptedBytes);
//            model.addAttribute("decryptedText", decryptedText);
//        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("error", "Decryption failed.");
//        }
//        return "encryptdecrypt";
//    }

    @RequestMapping("/generateKey")
    public String generateKey(Model model) {
        try {
            secretKey = KeyGenerator.getInstance("AES").generateKey();
            model.addAttribute("message", "AES key generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Key generation failed.");
        }
        return "encryptdecrypt";
    }
}