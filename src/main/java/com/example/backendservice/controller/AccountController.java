package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.model.dto.AuthDto;
import com.example.backendservice.model.request.AccountRequest;
import com.example.backendservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class AccountController extends BaseController {
    private final UserService userService;

    @RequestMapping(path = "register", method = POST)
    ResponseEntity<Boolean> register(AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.register(accountRequest));
    }

    @RequestMapping(path = "register/validate", method = POST)
    ResponseEntity<Boolean> validateRegister(AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.validate(accountRequest));
    }

    @RequestMapping(path = "login", method = POST)
    ResponseEntity<AuthDto> login(AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginTraditional(accountRequest));
    }
}
