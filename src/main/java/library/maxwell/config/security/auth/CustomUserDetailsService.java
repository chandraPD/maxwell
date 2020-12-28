package library.maxwell.config.security.auth;

import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Let people login with email
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));

        return UserPrincipal.create(user);
    }

    //This Method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Integer id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        return UserPrincipal.create(user);

    }
}
