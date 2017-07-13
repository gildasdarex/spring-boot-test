package com.pej.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pej.domains.Rolespermission;
import com.pej.domains.UsersRole;
import com.pej.domains.Utilisateur;
import com.pej.repository.PermissionRepository;
import com.pej.repository.PermissionRoleRepository;
import com.pej.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionRoleRepository permissionRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByUsername(username);
        List<Rolespermission> permit=new ArrayList<>();

        System.out.println(user.getFirstname());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UsersRole role : user.getUsersRoles())
            for (Rolespermission rolpermit : permissionRoleRepository.listePermissionConcerne(role.getRoles().getIdrole())) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permissionRepository.findOne(rolpermit.getIdpermission()).getName()));
            }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
