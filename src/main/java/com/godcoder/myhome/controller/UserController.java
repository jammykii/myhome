package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Role;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.model.UserRole;
import com.godcoder.myhome.repository.RoleRepository;
import com.godcoder.myhome.repository.UserRepository;
import com.godcoder.myhome.repository.UserRoleRepository;
//import com.godcoder.myhome.service.UserRoleService;;
import com.godcoder.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserService userService;
//    private UserRoleService userRoleService;

    @GetMapping("/usrole")
    public String role(Model model){
//        List<Role> role = roleRepository.findAll();
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/usrole";
    }

    @GetMapping("/modify")
    public String modify(Model model, @RequestParam(required = false) long id){
        List<Role> roles = roleRepository.findAll();
        User users = userRepository.findById(id).orElse(null);
        model.addAttribute("users", users);
        model.addAttribute("userRoles", roles);
        return "user/modify";
    }


    @PostMapping("/modify")
    public String postmodify(@Valid User user){
        long userId = user.getId();
        long roleId = user.getRoles().listIterator().next().getId();
        userRoleRepository.UserRoleSave(userId, roleId);
        return "redirect:/user/usrole";
    }
}