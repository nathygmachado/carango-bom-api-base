package br.com.caelum.carangobom.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.carangobom.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<AuthMapper> signInByEmailAndPassword(@Valid @RequestBody AuthForm form) throws AuthenticationException {

        UsernamePasswordAuthenticationToken loginData = form.converter();
        Authentication authentication = authenticationManager.authenticate(loginData);
        String token = tokenService.generate(authentication);
        return ResponseEntity.ok(new AuthMapper(token, "Bearer"));
    }
}   
