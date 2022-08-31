package com.sistema.blog.security;

import com.sistema.blog.entitys.roles.Rol;
import com.sistema.blog.entitys.roles.Usuario;
import com.sistema.blog.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public static final String USER_NO_FOUND_WITH_USERNAME_OR_MAIL = "Usuario no encontrado con el username: %s";
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return usuarioRepository
                .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .map(this::InstanceUser)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NO_FOUND_WITH_USERNAME_OR_MAIL, usernameOrEmail)));
    }

    private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles) {
        return roles
                .stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());
    }

    private User InstanceUser(Usuario usuario) {
        return new User(usuario.getUsername(), usuario.getPassword(), mapearRoles(usuario.getRoles()));
    }
}
