package team.smartworld.academy.todolist.service;

import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.exceptions.BadParameterException;
import team.smartworld.academy.todolist.exceptions.InvalidParameterException;
import team.smartworld.academy.todolist.exceptions.OutOfRangePriorityException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.UUID;

/**
 * Task service
 */
@Service
public class CheckParameterService {

    public static final int NAME_MAX_SIZE = 30;
    public static final int TITLE_MAX_SIZE = 256;

    private CheckParameterService() {
    }

    /**
     * @param id Task id
     * @return Long value id
     * @throws InvalidParameterException exception
     */

    public static UUID checkAndGetTaskId(String id) throws InvalidParameterException {
        try {
//            return Long.parseLong(id);
            return UUID.fromString(id);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.TASK_ID);
        }
    }

    /**
     * @param id Task List id
     * @return Long value id
     * @throws InvalidParameterException exception
     */
    public static UUID checkAndGetTaskListId(String id) throws InvalidParameterException {
        try {
//            return Long.parseLong(id);
            return UUID.fromString(id);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.TASK_LIST_ID);
        }
    }

    /**
     * @param mapParameters parameters map
     * @return isDone boolean variable
     * @throws BadParameterException     exception
     * @throws InvalidParameterException exception
     */
    public static boolean checkAndGetIsDone(Map<String, String> mapParameters)
            throws InvalidParameterException, BadParameterException {
        if (!mapParameters.containsKey("isDone")
                || mapParameters.get("isDone").isEmpty()) {
            throw new BadParameterException(BadParameterException.ExceptionType.IS_DONE);
        }
        if ("true".equalsIgnoreCase(mapParameters.get("isDone"))
                || "false".equalsIgnoreCase(mapParameters.get("isDone"))) {
            return Boolean.parseBoolean(mapParameters.get("isDone"));
        } else {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.IS_DONE);
        }
    }

    /**
     * @param mapData data
     * @return name value
     * @throws BadParameterException exception
     */
    public static String checkAndGetName(Map<String, String> mapData) throws BadParameterException {
        if (mapData.containsKey("name") && !mapData.get("name").isEmpty()) {
            String name = mapData.get("name").replaceAll("[^\\p{L}\\p{Z}]", "")
                    .trim();
            return name.length() < NAME_MAX_SIZE ? name : name.substring(0, NAME_MAX_SIZE);

        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.NAME);
        }
    }

    /**
     * @param mapData data
     * @return title value
     * @throws BadParameterException exception
     */
    public static String checkAndGetTitle(Map<String, String> mapData) throws BadParameterException {
        if (mapData.containsKey("title")) {
            String title = mapData.get("title").replaceAll("[^\\p{L}\\p{Z}]", "").trim();
            return title.length() < TITLE_MAX_SIZE ? title : title.substring(0, TITLE_MAX_SIZE);
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.TITLE);
        }
    }

    /**
     * @param mapData data
     * @return priority value
     * @throws BadParameterException       exception
     * @throws InvalidParameterException   exception
     * @throws OutOfRangePriorityException exception
     */
    public static byte checkAndGetPriority(Map<String, String> mapData)
            throws BadParameterException, OutOfRangePriorityException, InvalidParameterException {
        if (mapData.containsKey("priority") && !mapData.get("priority").isEmpty()) {
            int priority;
            try {
                priority = Integer.parseInt(mapData.get("priority"));
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(InvalidParameterException.ExceptionType.PRIORITY);
            }
            if (priority > 0 && priority <= 5) {
                return (byte) priority;
            } else {
                throw new OutOfRangePriorityException();
            }
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.PRIORITY);
        }
    }

    /**
     * @param mapData data
     * @return offset value
     * @throws BadParameterException     exception
     * @throws InvalidParameterException exception
     */
    public static long checkAndGetOffset(Map<String, String> mapData) throws BadParameterException, InvalidParameterException {
        if (mapData.containsKey("offset") && !mapData.get("offset").isEmpty()) {
            try {
                long offset = Long.parseLong(mapData.get("offset"));
                return Math.max(offset, 0L);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(InvalidParameterException.ExceptionType.OFFSET);
            }

        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.OFFSET);
        }
    }

    /**
     * @param mapData data
     * @return limit value
     * @throws BadParameterException     exception
     * @throws InvalidParameterException exception
     */
    public static int checkAndGetLimit(Map<String, String> mapData) throws BadParameterException, InvalidParameterException {
        if (mapData.containsKey("limit") && !mapData.get("limit").isEmpty()) {
            try {
                int limit = Integer.parseInt(mapData.get("limit"));
                return limit < 10 || limit > 100 ? 10 : limit;
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(InvalidParameterException.ExceptionType.LIMIT);
            }

        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.LIMIT);
        }
    }

    /**
     * @param mapData data
     * @return date created value
     * @throws BadParameterException     exception
     * @throws InvalidParameterException exception
     */
    public static LocalDateTime checkAndGetDateCreated(Map<String, String> mapData)
            throws BadParameterException, InvalidParameterException {
        if (mapData.containsKey("dateCreated") && !mapData.get("dateCreated").isEmpty()) {
            String dateCreatedString = mapData.get("dateCreated");
            try {
                return LocalDateTime.parse(dateCreatedString, DateTimeFormatter.ISO_INSTANT);
            } catch (DateTimeParseException e) {
                throw new InvalidParameterException(InvalidParameterException.ExceptionType.DATE_CREATED);
            }
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.DATE_CREATED);
        }
    }

    /**
     * @param mapData data
     * @return date change value
     * @throws InvalidParameterException exception
     * @throws BadParameterException     exception
     */
    public static LocalDateTime checkAndGetDateChange(Map<String, String> mapData)
            throws InvalidParameterException, BadParameterException {
        if (mapData.containsKey("dateChange") && !mapData.get("dateChange").isEmpty()) {
            String dateChangeString = mapData.get("dateChange");
            try {
                return LocalDateTime.parse(dateChangeString, DateTimeFormatter.ISO_INSTANT);
            } catch (DateTimeParseException e) {
                throw new InvalidParameterException(InvalidParameterException.ExceptionType.DATE_CHANGE);
            }
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.DATE_CHANGE);
        }
    }

    /**
     * @param mapData data
     * @return created sort boolean value
     * @throws BadParameterException     exception
     * @throws InvalidParameterException exception
     */
    public static boolean checkAndGetDateCreatedSort(Map<String, String> mapData)
            throws BadParameterException, InvalidParameterException {
        if (!mapData.containsKey("dateCreatedSort")
                || mapData.get("dateCreatedSort").isEmpty()) {
            throw new BadParameterException(BadParameterException.ExceptionType.DATE_CREATED_SORT);
        }
        if ("true".equalsIgnoreCase(mapData.get("dateCreatedSort"))
                || "false".equalsIgnoreCase(mapData.get("dateCreatedSort"))) {
            return Boolean.parseBoolean(mapData.get("dateCreatedSort"));
        } else {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.DATE_CREATED_SORT);
        }
    }

    /**
     * @param mapData data
     * @return date change sort value
     * @throws BadParameterException     exception
     * @throws InvalidParameterException exception
     */
    public static boolean checkAndGetDateChangeSort(Map<String, String> mapData)
            throws BadParameterException, InvalidParameterException {
        if (!mapData.containsKey("dateChangeSort")
                || mapData.get("dateChangeSort").isEmpty()) {
            throw new BadParameterException(BadParameterException.ExceptionType.DATE_CHANGE_SORT);
        }
        if ("true".equalsIgnoreCase(mapData.get("dateChangeSort"))
                || "false".equalsIgnoreCase(mapData.get("dateChangeSort"))) {
            return Boolean.parseBoolean(mapData.get("dateChangeSort"));
        } else {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.DATE_CHANGE_SORT);
        }
    }

    /**
     * @param mapData data
     * @return name sort String value
     * @throws BadParameterException     exception
     * @throws InvalidParameterException exception
     */
    public static boolean checkAndGetNameSort(Map<String, String> mapData)
            throws BadParameterException, InvalidParameterException {
        if (!mapData.containsKey("nameSort")
                || mapData.get("nameSort").isEmpty()) {
            throw new BadParameterException(BadParameterException.ExceptionType.NAME_SORT);
        }
        if ("true".equalsIgnoreCase(mapData.get("nameSort"))
                || "false".equalsIgnoreCase(mapData.get("nameSort"))) {
            return Boolean.parseBoolean(mapData.get("nameSort"));
        } else {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.NAME_SORT);
        }
    }

    /**
     * @param mapData data
     * @return isDone value
     * @throws BadParameterException     exception
     * @throws InvalidParameterException exception
     */
    public static boolean checkAndGetIsDoneSort(Map<String, String> mapData)
            throws BadParameterException, InvalidParameterException {
        if (!mapData.containsKey("isDoneSort")
                || mapData.get("isDoneSort").isEmpty()) {
            throw new BadParameterException(BadParameterException.ExceptionType.IS_DONE_SORT);
        }
        if ("true".equalsIgnoreCase(mapData.get("isDoneSort"))
                || "false".equalsIgnoreCase(mapData.get("isDoneSort"))) {
            return Boolean.parseBoolean(mapData.get("isDoneSort"));
        } else {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.IS_DONE_SORT);
        }
    }

}
