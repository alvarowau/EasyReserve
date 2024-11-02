package org.alvarowau.service;


import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.ActionLogDTO;
import org.alvarowau.model.dto.ActionLogResponseAccountStatusChange;
import org.alvarowau.model.dto.mapper.MapperActionLog;
import org.alvarowau.repository.ActionLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionLogService {

    private final ActionLogRepository actionLogRepository;
    private final MapperActionLog mapperActionLog;

    public ActionLogResponseAccountStatusChange generateActionLog(ActionLogResponseAccountStatusChange responseDelete) {
        return responseDelete;

    }

    public void saveAction(ActionLogDTO action) {
        actionLogRepository.save(mapperActionLog.toEntity(action));
    }
}
