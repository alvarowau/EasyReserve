package org.alvarowau.model.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.action.ActionLogDTO;
import org.alvarowau.model.entity.ActionLog;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperActionLog {

    private final ModelMapper modelMapper;

    public ActionLog toEntity(ActionLogDTO dto) {
        return ActionLog.builder()
                .actionType(dto.actionType())
                .userId(dto.userId())
                .affectedUserId(dto.affectedUserId())
                .reason(dto.reason())
                .build();
    }

    public ActionLogDTO toDto(ActionLog entity) {
        return modelMapper.map(entity, ActionLogDTO.class);
    }


}
