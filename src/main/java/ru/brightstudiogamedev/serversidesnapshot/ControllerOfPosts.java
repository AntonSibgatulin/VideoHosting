package ru.brightstudiogamedev.serversidesnapshot;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerOfPosts {

    @PostMapping(value = "/lenta")
    public String getLenta(Model model){

        return null;
    }

}
