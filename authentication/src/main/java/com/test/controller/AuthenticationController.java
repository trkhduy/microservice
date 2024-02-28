package com.test.controller;

import com.test.dto.request.AuthenticationRequest;
import com.test.dto.request.UserRequest;
import com.test.dto.response.AuthenticationResponse;
import com.test.dto.response.UserResponse;
import com.test.service.impl.AuthenticationService;
import com.test.utils.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        String logMessage = "(register) Request : {}" + request;
        log.info(LogMessage.logInfo(logMessage));
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Validated @RequestBody AuthenticationRequest authenticationRequest){

        return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest),HttpStatus.OK);
    }

    @GetMapping("/authorize")
    public Boolean authorize(@RequestParam("token") String token, @RequestParam("uri") String uri){
        System.out.println("chay ngay di");
        return authenticationService.authorize(token, uri);
    }
}
