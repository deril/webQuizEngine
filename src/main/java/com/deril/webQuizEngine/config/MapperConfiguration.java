package com.deril.webQuizEngine.config;

import static org.mapstruct.MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.MapperConfig;

@MapperConfig(
    componentModel = "spring",
    mappingInheritanceStrategy = AUTO_INHERIT_ALL_FROM_CONFIG,
    unmappedSourcePolicy = IGNORE,
    unmappedTargetPolicy = IGNORE
)
public interface MapperConfiguration {

}
