package com.sistema.sah.reservas.utils.map;

import com.sistema.sah.commons.dto.TipoCuartoDto;
import com.sistema.sah.commons.entity.TipoCuartoEntity;
import com.sistema.sah.reservas.entity.VistaTiposCuartosEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VistaTipoCuartoMapper {

    List<TipoCuartoDto> listVistaTipoCuartoToListTipoCuarto(List<VistaTiposCuartosEntity> vistaTiposCuartosEntities);

}
