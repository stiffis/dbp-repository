package com.purrComplexity.TrabajoYa.User;

import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.dto.JwtAuthenticationResponse;
import com.purrComplexity.TrabajoYa.User.dto.SigninRequest;
import com.purrComplexity.TrabajoYa.User.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    UserAccountRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder; //Necesita estar configurado

    @Autowired
    JwtService jwtService; //Necesita estar configurado

    @Autowired
    AuthenticationManager authenticationManager; //Necesita estar configurado

    public JwtAuthenticationResponse signup(SignupRequest request) {
        // Crear una nueva instancia de UserAccount y mapear los campos correctamente
        UserAccount user = new UserAccount();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getCorreo()); // Mapear correo del frontend a email del backend
        user.setUsernameField(request.getUsername()); // Mapear username correctamente
        user.setRole(request.getRole());
        
        // Inicializar campos de estado del usuario para permitir login
        user.setExpired(false);
        user.setLocked(false);
        user.setCredentialsExpired(false);
        user.setEnable(true);

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);
        response.setUsername(user.getUsernameField());
        response.setRole(user.getRole());
        response.setIsEmpresario(false);
        response.setIsTrabajador(false);

        return response;
    }

    public JwtAuthenticationResponse signin(SigninRequest request) throws IllegalArgumentException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())); //Si cambio la forma de registrarme, cambio la carpeta de filter
        var user = userRepository.findByEmail(request.getEmail());
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);


        response.setUsername(user.getUsernameField());
        response.setIsTrabajador(user.getIsTrabajador());
        response.setIsEmpresario(user.getIsEmpresario());
        response.setRole(user.getRole());

        return response;
    }
}
