package com.example.demo.application.command;

import com.example.core.application.command.CommandHandler;
import com.example.core.domain.event.DomainEventPublisher;
import com.example.core.domain.exception.BusinessRuleViolationException;
import com.example.demo.domain.model.Email;
import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreateUserCommand.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, Long> {
    
    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;
    
    @Override
    public Long handle(CreateUserCommand command) {
        // Validate business rules
        Email email = Email.of(command.getEmail());
        
        if (userRepository.existsByEmail(email)) {
            throw new BusinessRuleViolationException(
                "User with email " + email + " already exists");
        }
        
        // Create and save user
        User user = User.create(
            command.getFirstName(),
            command.getLastName(),
            email
        );
        
        User savedUser = userRepository.save(user);
        
        // Publish domain events
        eventPublisher.publishAll(savedUser.getDomainEvents());
        savedUser.clearDomainEvents();
        
        return savedUser.getId();
    }
}
