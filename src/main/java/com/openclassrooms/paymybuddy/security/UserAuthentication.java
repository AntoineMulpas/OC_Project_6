package com.openclassrooms.paymybuddy.security;

import org.springframework.security.core.Authentication;


public interface UserAuthentication {

    Authentication getAuthentication();

}
