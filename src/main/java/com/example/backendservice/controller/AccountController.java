package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.model.dto.AuthDto;
import com.example.backendservice.model.request.AccountRequest;
import com.example.backendservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class AccountController extends BaseController {
    private final UserService userService;

    @RequestMapping(path = "register", method = POST)
    ResponseEntity<Boolean> register(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.register(accountRequest));
    }

    @RequestMapping(path = "register/validate", method = POST)
    ResponseEntity<Boolean> validateRegister(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.validate(accountRequest));
    }

    @RequestMapping(path = "login", method = POST)
    ResponseEntity<AuthDto> login(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginTraditional(accountRequest));
    }

    @PostMapping(path = "refreshToken")
    ResponseEntity<AuthDto> refreshToken(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.refreshToken(accountRequest.getRefreshToken()));
    }
}
