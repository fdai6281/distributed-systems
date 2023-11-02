package com.hsfulda.distributedsystems.exercises.week_one;

import com.hsfulda.distributedsystems.routing.Routes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WeekOneController {
    private static final String defaultRoute = Routes.weekOne;

    // Dependency injection analog to @Autowired
    private final PhoneNumberRepository phoneNumberRepository;

    public WeekOneController(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @GetMapping(defaultRoute)
    public String getPage(Model model) {
        model.addAttribute("phoneNumbers", getAllPhoneNumbers());
        return defaultRoute;
    }

    @PostMapping(value = defaultRoute + "/save", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String savePhoneNumber(@ModelAttribute PhoneNumberModel model) {
        PhoneNumberDBEntity entity = new PhoneNumberDBEntity();

        entity.setName(model.getName());
        entity.setPhoneNumber(model.getPhoneNumber());
        phoneNumberRepository.save(entity);

        return "redirect:/" + defaultRoute;
    }

    private List<PhoneNumberDBEntity> getAllPhoneNumbers() {
        // This returns a JSON or XML with the users
        return phoneNumberRepository.findAll();
    }
}
