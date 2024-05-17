package src.main.java.com.myapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import src.main.java.com.myapp.models.Message;
import  src.main.java.com.myapp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Controller
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

//    @PostMapping("/sendEncryptedMessage")
//    public String sendEncryptedMessage(String message, Model model) {
//        System.out.println("Inside sendEncryptedMessage");
//        System.out.println(message);
//
//        // Generate AES key
//        String aesKey = messageService.generateAESKey();
//        System.out.println(aesKey);
//        // Encrypt the message using the generated AES key
//        String encryptedMessage = messageService.encryptMessage(message, aesKey);
//        System.out.println(encryptedMessage);
//
//        // Save the encrypted message to the database
//        Message savedMessage = messageService.saveEncryptedMessage(encryptedMessage);
//
//        // Add the saved message and AES key to the model
//        model.addAttribute("savedMessage", savedMessage);
//        model.addAttribute("aesKey", aesKey);
//
//        // Redirect to the index page or any other appropriate page
//        return "redirect:/index"; // Replace "/index" with the appropriate URL mapping for your index page
//    }

    @PostMapping("/sendEncryptedMessage")
    public String sendEncryptedMessage(@RequestParam("message") String message, Model model) {

        System.out.println("Inside sendEncryptedMessage");


//        String message = requestBody.get("message");
        System.out.println(message);

        if (message == null || message.isEmpty()) {
            // Handle case where message is null or empty
            model.addAttribute("error", "Message cannot be empty");
            return "errorPage"; // Return the view for error page
        }

        // Generate AES key
        String aesKey = generateAESKey1();

        // Encrypt the message using the generated AES key
        String encryptedMessage = encryptMessage1(message, aesKey);

        // Generate AES key
//        String aesKey = messageService.generateAESKey();

        // Encrypt the message using the generated AES key
//        String encryptedMessage = messageService.encryptMessage(message, aesKey);
        System.out.println("**************encryptedMessage**************");
        System.out.println("encryptedMessage: "+encryptedMessage);
        System.out.println("aesKey: "+aesKey);

        // Save the encrypted message to the database
        Message savedMessage = messageService.saveEncryptedMessage(encryptedMessage, aesKey);

        // Add the saved message and AES key to the model
        model.addAttribute("savedMessage", savedMessage);
        model.addAttribute("aesKey", aesKey);

        return "index"; // Return the view for the home page
    }

    @PostMapping("/generateAESKey")
    public String generateAESKey(Model model) {
        // Generate AES key
        String aesKey = messageService.generateAESKey();

        // Save the AES key to the database
        messageService.saveAESKey(aesKey);

        model.addAttribute("aesKey", aesKey);

        return "aesKeyGeneratedConfirmation"; // Return the view for AES key generation confirmation
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        System.out.println("All Messages:");
        for (Message message : messages) {
            System.out.println("ID: " + message.getId() + ", Encrypted Message: " + message.getEncryptedMessage() + ", AES Key: " + message.getAesKey() + ", Created At: " + message.getCreatedAt());
        }
        return messages;
    }

    private String encryptMessage1(String message, String aesKey) {
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

    private String generateAESKey1() {
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
