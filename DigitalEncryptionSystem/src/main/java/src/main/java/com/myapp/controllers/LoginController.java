// LoginController.java
package src.main.java.com.myapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import src.main.java.com.myapp.models.LoginCredentials;
import src.main.java.com.myapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    private final AuthService authService;

    @Autowired
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("/login")
    public ModelAndView login(Model model) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("credentials", new LoginCredentials());
        return modelAndView;
    }

    @PostMapping("/authenticate")
    public ModelAndView authenticate(LoginCredentials credentials, Model model, HttpSession session) {
        ModelAndView modelAndView;
        if (authService.authenticate(credentials)) {
            session.setAttribute("loggedIn", true);
            modelAndView = new ModelAndView("redirect:/receiver");
        } else {
            modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Invalid username or password");
        }
        return modelAndView;
    }

    @PostMapping("/addUser")
    public ModelAndView addUser(LoginCredentials credentials, Model model) {
        ModelAndView modelAndView;
        boolean userAdded = authService.addUser(credentials);
        if (userAdded) {
            modelAndView = new ModelAndView("login");
            modelAndView.addObject("message", "User added successfully");
        } else {
            modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Failed to add user. Please try again.");
        }
        return modelAndView;
    }

    @RequestMapping("/receiver")
    public ModelAndView receiver(Model model, HttpSession session) {
        ModelAndView modelAndView;
        if (session.getAttribute("loggedIn") != null) {
            modelAndView = new ModelAndView("receiver");
        } else {
            modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Please login to access this page.");
        }
        return modelAndView;
    }

//    @PostMapping("/decrypt")
//    public ModelAndView decrypt(String encryptedText, Model model, HttpSession session) {
//        ModelAndView modelAndView;
//        if (session.getAttribute("loggedIn") != null) {
//            // Decrypt logic here
//            // Add decrypted text to model
//            model.addAttribute("decryptedText", "Decrypted text goes here");
//            modelAndView = new ModelAndView("receiver");
//        } else {
//            modelAndView = new ModelAndView("redirect:/login");
//        }
//        return modelAndView;
//    }

    @GetMapping("/users")
    @ResponseBody
    public List<String> getAllUsers() {
        return authService.getAllUsers();
    }
}
