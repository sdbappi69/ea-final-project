package miu.edu.controller;


import lombok.RequiredArgsConstructor;
import miu.edu.dto.AuthDTO.AuthLoginDTO;
import miu.edu.service.Impl.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationServiceImpl authenticationService;


    @PostMapping("/login/authenticate")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginDTO authLoginDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authLoginDTO));
    }

}
