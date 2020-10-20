package team.smartworld.academy.todolist.service;

import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.exceptions.*;

import java.util.Map;

/**
 * Task service
 */
@Service
public class CheckParameterService {

    private CheckParameterService() {
    }

    /**
     * @param id Task id
     * @return Long value id
     * @throws InvalidTaskIdException exception
     */

    public static Long checkAndGetTaskId(String id) throws InvalidTaskIdException {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidTaskIdException();
        }
    }

    /**
     * @param id Task List id
     * @return Long value id
     * @throws InvalidTaskListIdException exception
     */
    public static Long checkAndGetTaskListId(String id) throws InvalidTaskListIdException {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidTaskListIdException();
        }
    }

    /**
     * @param mapParameters parameters map
     * @return isDone boolean variable
     * @throws BadIsDoneException        exception
     * @throws InvalidParameterException exception
     */
    public static boolean checkAndGetIsDone(Map<String, String> mapParameters) throws BadIsDoneException, InvalidParameterException {
        if (!mapParameters.containsKey("isDone")
                || mapParameters.get("isDone").isEmpty()) {
            throw new BadIsDoneException();
        }
        if ("true".equalsIgnoreCase(mapParameters.get("isDone")) || "false".equalsIgnoreCase(mapParameters.get("isDone"))) {
            return Boolean.parseBoolean(mapParameters.get("isDone"));
        } else {
            throw new InvalidParameterException("isDone");
        }
    }

    public static String checkAndGetName(Map<String, String> mapData) throws BadNameException {
        if (mapData.containsKey("name") && !mapData.get("name").isEmpty()) {
            return mapData.get("name").replaceAll("[^\\p{L}\\p{Z}]", "").trim();
        } else {
            throw new BadNameException();
        }
    }

    public static String checkAndGetTitle(Map<String, String> mapData) throws BadTitleException {
        if (mapData.containsKey("title")) {
            return mapData.get("title").replaceAll("[^\\p{L}\\p{Z}]", "").trim();
        } else {
            throw new BadTitleException();
        }
    }

    public static byte checkAndGetPriority(Map<String, String> mapData) throws BadPriorityException, InvalidNumberFormatPriorityException, OutOfRangePriorityException {
        if (mapData.containsKey("priority") && !mapData.get("priority").isEmpty()) {
            int priority;
            try {
                priority = Integer.parseInt(mapData.get("priority"));
            } catch (NumberFormatException e) {
                throw new InvalidNumberFormatPriorityException();
            }
            if (priority > 0 && priority <= 5) {
                return (byte) priority;
            } else {
                throw new OutOfRangePriorityException();
            }
        } else {
            throw new BadPriorityException();
        }
    }
}
