package src.main.java.com.myapp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
public class EncryptedDataController {

    @PostMapping("/encryptedtext")
    public String encrypt(@RequestParam("message") String message) {
        // Generate AES key
        String aesKey = generateAESKey();

        // Encrypt the message using the generated AES key
        String encryptedMessage = encryptMessage(message, aesKey);

        // Return the AES key and encrypted message as a concatenated string
        return "AES Key: " + aesKey + "\nEncrypted Message: " + encryptedMessage;
    }

    private String encryptMessage(String message, String aesKey) {
        try {
            // Initialize AES cipher with the provided key
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Encrypt the message
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());

            // Encode the encrypted bytes to Base64
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return null;
        }
    }

    private String generateAESKey() {
        try {
            // Generate AES key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();

            // Encode the key to Base64
            byte[] encodedKey = secretKey.getEncoded();
            return Base64.getEncoder().encodeToString(encodedKey);
        } catch (NoSuchAlgorithmException e) {
            // Handle NoSuchAlgorithmException
            e.printStackTrace();
            return null;
        }
    }
}
