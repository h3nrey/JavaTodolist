package br.com.h3nrey.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping("/create")
    public String CreateUser(@RequestBody UserModel userModel) {
        System.out.println("created " + userModel.getUsername() + " user successfully");
        return userModel.getUsername();
    }
}
