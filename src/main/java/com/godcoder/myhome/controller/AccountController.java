package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Account;
import com.godcoder.myhome.model.Role;
import com.godcoder.myhome.repository.RoleRepository;
import com.godcoder.myhome.repository.AccountRepository;
import com.godcoder.myhome.repository.UserRoleRepository;
import com.godcoder.myhome.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AccountService accountService;


    @GetMapping("/usrole")
    public String role(Model model){
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "account/usrole";
    }

    @GetMapping("/modify")
    public String modify(Model model, @RequestParam(required = false) long id){
        List<Role> roles = roleRepository.findAll();
        Account users = accountRepository.findById(id).orElse(null);
        model.addAttribute("accounts", users);
        model.addAttribute("userRoles", roles);
        return "account/modify";
    }

    @PostMapping("/modify")
    public String postmodify(@Valid Account account){
        long userId = account.getId();
        long roleId = account.getRoles().listIterator().next().getId();
        userRoleRepository.UserRoleSave(userId, roleId);

        return "redirect:/account/usrole";
    }
}