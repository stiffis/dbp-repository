package com.purrComplexity.TrabajoYa.User;

import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserAccountRepository repository;//Aqui nos indica donde se va a guardar
                                    //Ademas mi entidad tiene que implementar de UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    public UserDetailsService userDetailsService() {
        return this;
    }
}
