package app.web;

import app.entities.User;
import app.mail.EmailService;
import app.mail.EmailServiceImpl;
import app.repositories.IUserRepository;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    private final IUserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public HomeController(IUserRepository userRepository,
                          UserService userService,
                          PasswordEncoder passwordEncoder,
                          EmailService emailService)
    {
        this.userRepository=userRepository;
        this.userService = userService;
        this.passwordEncoder=passwordEncoder;
        this.emailService= emailService;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<User> users = userRepository.findAll();//userRepository.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    @PostMapping("/create")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create";
        }

        if(userRepository.findByEmail(user.getEmail()) != null){
            model.addAttribute("error", "Email is used");
            return "create";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        emailService.sendSimpleMessage(user.getEmail(),"Успішна реєстрації","Спасібо");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "edit";
        }
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/";
    }
}
