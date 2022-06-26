package br.com.fiap.keycloakmiddleware.application.controller;

import br.com.fiap.keycloakmiddleware.domain.model.Auth;
import br.com.fiap.keycloakmiddleware.domain.model.User;
import br.com.fiap.keycloakmiddleware.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity createNewUser(@RequestBody User user) {
        userService.createNewUser(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")
    public Auth authenticateUser(@RequestBody User user) {
        return userService.getUserToken(user);
    }
}
