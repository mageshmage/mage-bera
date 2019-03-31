package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.StateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity State and its DTO StateDTO.
 */
@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface StateMapper extends EntityMapper<StateDTO, State> {

	@Mappings({ @Mapping(source = "country.id", target = "countryId"),
			@Mapping(source = "country.countryName", target = "countryName") })
	StateDTO toDto(State state);

	@Mapping(target = "cities", ignore = true)
	@Mapping(source = "countryId", target = "country")
	State toEntity(StateDTO stateDTO);

	default State fromId(Long id) {
		if (id == null) {
			return null;
		}
		State state = new State();
		state.setId(id);
		return state;
	}
}
