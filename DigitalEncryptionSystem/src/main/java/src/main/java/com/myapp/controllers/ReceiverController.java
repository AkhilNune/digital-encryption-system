package src.main.java.com.myapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import src.main.java.com.myapp.models.Message;
import src.main.java.com.myapp.services.MessageService;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Controller
@RequestMapping("/receiver")
public class ReceiverController {

    private final MessageService messageService;

    public ReceiverController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String showReceiverPage(Model model) {
        // Fetch all messages from the service
        List<Message> messages = messageService.getAllMessages();

        // Decrypt each message and store the decrypted messages in a list
        List<String> decryptedMessages = new ArrayList<>();
        for (Message message : messages) {
//            String decryptedText = messageService.decryptMessage(message.getEncryptedMessage(), message.getAesKey());
            System.out.println("passing to decryptMessage");

            String encryptedText = message.getEncryptedMessage();
            String aesKey = message.getAesKey();

//            String encryptedText = "CmJ3SQYW5WRwBYHN/QCEdg==";
//            String aesKey = "CnNnCfijkw9q4Mk/AGafws3GwbF24zLcD3LiQJOTCO4=";

            System.out.println("encryptedText: "+encryptedText);
            System.out.println("aesKey: "+aesKey);

            String decryptedText = decrypt(encryptedText, aesKey);
//            String decryptedText = decrypt(decryptedText1, aesKey);

            if (decryptedText != null) {
                System.out.println("Decrypted Text: " + decryptedText);
            } else {
                System.out.println("Decryption failed.");
            }

            System.out.println(message.getEncryptedMessage());
            System.out.println(message.getAesKey());
            System.out.println(decryptedText);

            System.out.println("***********************************");
            decryptedMessages.add(decryptedText);
        }

        // Pass the list of decrypted messages to the template
        model.addAttribute("decryptedMessages", decryptedMessages);

        return "receiver"; // Return the view for the receiver page
    }

        public String decrypt(String encryptedText, String aesKey) {
            try {
                // Decode the AES key from Base64
                byte[] decodedKey = Base64.getDecoder().decode(aesKey);
                SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");

                // Initialize AES cipher with the provided key
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);

                // Decode the encrypted message from Base64
                byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

                // Decrypt the message
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                // Convert decrypted bytes to String
                return new String(decryptedBytes);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

