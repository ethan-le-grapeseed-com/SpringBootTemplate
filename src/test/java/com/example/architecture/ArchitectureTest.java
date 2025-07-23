package com.example.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

/**
 * Architecture tests to enforce Clean Architecture rules.
 */
class ArchitectureTest {
    
    private final JavaClasses importedClasses = new ClassFileImporter()
        .importPackages("com.example");
    
    @Test
    void domain_should_not_depend_on_application() {
        ArchRule rule = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAPackage("..application..");
        
        rule.check(importedClasses);
    }
    
    @Test
    void domain_should_not_depend_on_infrastructure() {
        ArchRule rule = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAPackage("..infrastructure..");
        
        rule.check(importedClasses);
    }
    
    @Test
    void domain_should_not_depend_on_presentation() {
        ArchRule rule = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAPackage("..presentation..");
        
        rule.check(importedClasses);
    }
    
    @Test
    void application_should_not_depend_on_infrastructure() {
        ArchRule rule = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat()
            .resideInAPackage("..infrastructure..");
        
        rule.check(importedClasses);
    }
    
    @Test
    void application_should_not_depend_on_presentation() {
        ArchRule rule = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat()
            .resideInAPackage("..presentation..");
        
        rule.check(importedClasses);
    }
    
    @Test
    void entities_should_be_in_domain_model_package() {
        ArchRule rule = classes()
            .that().areAnnotatedWith("jakarta.persistence.Entity")
            .should().resideInAPackage("..domain.model..");
        
        rule.check(importedClasses);
    }
    
    @Test
    void repositories_should_be_interfaces() {
        ArchRule rule = classes()
            .that().haveNameMatching(".*Repository")
            .and().resideInAPackage("..domain.repository..")
            .should().beInterfaces();
        
        rule.check(importedClasses);
    }
    
    @Test
    void controllers_should_be_in_presentation_package() {
        ArchRule rule = classes()
            .that().areAnnotatedWith("org.springframework.web.bind.annotation.RestController")
            .should().resideInAPackage("..presentation.controller..");
        
        rule.check(importedClasses);
    }
    
    @Test
    void command_handlers_should_be_in_application_package() {
        ArchRule rule = classes()
            .that().haveNameMatching(".*CommandHandler")
            .should().resideInAPackage("..application.command..");
        
        rule.check(importedClasses);
    }
    
    @Test
    void query_handlers_should_be_in_application_package() {
        ArchRule rule = classes()
            .that().haveNameMatching(".*QueryHandler")
            .should().resideInAPackage("..application.query..");
        
        rule.check(importedClasses);
    }
}
