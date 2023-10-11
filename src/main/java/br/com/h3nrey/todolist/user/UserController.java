package br.com.h3nrey.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity CreateUser(@RequestBody UserModel userModel) {
        // Check if user exists
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        // Hash password
        var hashedPassword = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(hashedPassword);

        // Save on database
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
