package com.pej.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pej.domains.Utilisateur;
import com.pej.repository.RolesRepository;
import com.pej.repository.UsersRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Utilisateur user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setUsersRoles(new HashSet<>(roleRepositor));
        usersRepository.save(user);
    }

    @Override
    public boolean authenticate(String username, String password) {
        Utilisateur user= usersRepository.verifExistantUser(username,password);
        System.out.println("auth");
        if(user==null)
            return false;
        else
            return true;
    }

    @Override
    public Utilisateur findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
