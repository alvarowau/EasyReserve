package org.alvarowau.service;


import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.action.ActionLogDTO;
import org.alvarowau.model.dto.action.ActionLogResponseAccountStatusChange;
import org.alvarowau.model.dto.action.ActionSummary;
import org.alvarowau.model.dto.mapper.MapperActionLog;
import org.alvarowau.model.entity.ActionLog;
import org.alvarowau.model.enums.ActionType;
import org.alvarowau.repository.ActionLogRepository;
import org.alvarowau.user.model.entity.BaseUser;
import org.alvarowau.user.service.util.UserIdFinderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionLogService {

    private final ActionLogRepository actionLogRepository;
    private final MapperActionLog mapperActionLog;
    private final UserIdFinderService userIdFinderService;

    public ActionLogResponseAccountStatusChange generateActionLog(ActionLogResponseAccountStatusChange responseDelete) {
        return responseDelete;
    }

    public void saveActionLog(ActionLogDTO action) {
        actionLogRepository.save(mapperActionLog.toEntity(action));
    }

    public List<ActionSummary> getAllActionLogs() {
        List<ActionLog> actionLogs = actionLogRepository.findAll();
        return convertActionLogsToSummaries(actionLogs);

    }

    public List<ActionSummary> getActionLogsByActionType(String actionType) {
        actionType = actionType.toUpperCase();
        ActionType action;
        try {
            action = ActionType.valueOf(actionType);
            List<ActionLog> actionLogs = actionLogRepository.findByActionType(action);
            return convertActionLogsToSummaries(actionLogs);
        } catch (IllegalArgumentException e) {
            return getAllActionLogs();
        }
    }

    private List<ActionSummary> convertActionLogsToSummaries(List<ActionLog> actionLogs) {
        List<ActionSummary> actionSummaries = new ArrayList<>();
        for (ActionLog actionLog : actionLogs) {
            actionSummaries.add(convertActionLogToSummary(actionLog));
        }
        return actionSummaries;
    }

    private ActionSummary convertActionLogToSummary(ActionLog actionLog) {
        BaseUser user = userIdFinderService.findUserById(actionLog.getUserId());
        String initiatorUsername = user.getUsername();
        String targetUsername;
        if (actionLog.getUserId() == actionLog.getAffectedUserId()) {
            targetUsername = initiatorUsername;
        } else {
            user = userIdFinderService.findUserById(actionLog.getAffectedUserId());
            targetUsername = user.getUsername();
        }
        return ActionSummary.builder()
                .actionType(actionLog.getActionType())
                .initiatorUsername(initiatorUsername)
                .targetUsername(targetUsername)
                .reason(actionLog.getReason())
                .date(actionLog.getTimestamp().toLocalDate())
                .build();
    }

}