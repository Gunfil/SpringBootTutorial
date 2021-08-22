package tutorial.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tutorial.domain.Message;
import tutorial.domain.User;
import tutorial.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting(Model model) {

        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String create(@AuthenticationPrincipal User user,
                         @RequestParam String text,
                         @RequestParam String tag, Model model) {
        Message message = new Message(text, tag, user);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Dear");
        list.add("User");
        model.addAttribute("data", list);
        return "hello";
    }
}
