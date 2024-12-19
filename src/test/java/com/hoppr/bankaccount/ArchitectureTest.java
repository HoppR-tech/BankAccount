package com.hoppr.bankaccount;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {

    @Test
    public void domain_usecases_should_be_instanciated_by_spring_framework() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.hoppr.bankaccount");

        ArchRule rule = classes().that()
                .resideInAPackage("..domain.usecases..")
                .and()
                .haveSimpleNameEndingWith("UseCase")
                .should()
                .beAnnotatedWith(Service.class);

        rule.check(importedClasses);
    }

}
