package br.com.jzsports.tournament_control.model.mapper.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@MapperConfig
public interface IgnoreImmutableConfig {
    @Mapping(target = "createdAt", ignore = true)
    void ignoreImmutableFields(@MappingTarget Object source, Object target);
}
