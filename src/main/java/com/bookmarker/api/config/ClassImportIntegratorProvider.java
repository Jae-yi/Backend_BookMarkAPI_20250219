package com.bookmarker.api.config;

import com.bookmarker.api.dto.BookmarkDTO;
import io.hypersistence.utils.hibernate.type.util.ClassImportIntegrator;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.List;

public class ClassImportIntegratorProvider implements IntegratorProvider {
 
    @Override
    public List<Integrator> getIntegrators() {
        return List.of(
            new ClassImportIntegrator(
                // list all DTOs for which you don't want to use full pacakge in JPQL query
                List.of(
                    BookmarkDTO.class
                )
            )
        );
    }
}