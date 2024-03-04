package com.bankoperations.test.controller;

import com.bankoperations.test.dto.LoginRequest;
import com.bankoperations.test.dto.TokenResponse;
import com.bankoperations.test.security.JwtSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bankoperations.test.controller.core.ControllerConst.API;

@RestController
@RequestMapping(API+"/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtSecurityService jwtSecurityService;


    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return jwtSecurityService.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh() {
        return jwtSecurityService.refresh();
    }


}
