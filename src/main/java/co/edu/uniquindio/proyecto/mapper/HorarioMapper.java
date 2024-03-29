package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dtos.HorarioDTO;
import co.edu.uniquindio.proyecto.modelo.Horario;

import org.mapstruct.*;


import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.WARN )
public interface HorarioMapper {

    @Mappings({
            @Mapping(source = "dia", target = "dia"),
            @Mapping(source = "horaInicio", target = "horaInicio"),
            @Mapping(source = "horaFin", target = "horaFin")
    })
    HorarioDTO toGetDTO(Horario horario);

    @InheritInverseConfiguration
    Horario toEntity(HorarioDTO horarioDTO);

    List<HorarioDTO> toGetHorarioList(List<Horario> horarios);

    List<Horario> toEntityList(List<HorarioDTO> horarios);
}
