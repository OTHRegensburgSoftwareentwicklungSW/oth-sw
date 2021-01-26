package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.ServiceType;
import org.springframework.stereotype.Service;

/**
 * Decorator class of {@link UserService} filtering by {@link ServiceType#BANK}.
 */
@Service
public class BankUserService extends PartnerUserService {

    protected BankUserService(UserService userService) {
        super(ServiceType.BANK, userService);
    }

}
