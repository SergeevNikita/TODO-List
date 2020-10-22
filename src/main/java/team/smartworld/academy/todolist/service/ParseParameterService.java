package team.smartworld.academy.todolist.service;

import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.exceptions.*;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

/**
 * Сервис для проверки на корректность и преобразования значений параметров обьекта TaskList
 */
@Service
public class ParseParameterService {

    public static final int NAME_MAX_SIZE = 30;

    public static final int TITLE_MAX_SIZE = 255;

    private ParseParameterService() {
    }

    /**
     * Метод для получения UUID из строки для обьекта Task
     *
     * @param id принемает строку Task id
     * @return возвращает значение UUID
     * @throws InvalidParameterException неверный формат параметра
     */
    public static UUID parseTaskId(String id) throws InvalidParameterException {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.TASK_ID);
        }
    }

    /**
     * Метод для получения UUID из строки для обьекта TaskList
     *
     * @param id принемает строку TaskList id
     * @return возвращает значение UUID
     * @throws InvalidParameterException неверный формат параметра
     */
    public static UUID parseTaskListId(String id) throws InvalidParameterException {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.TASK_LIST_ID);
        }
    }

    /**
     * Метод извлекает параметр 'done' и преобразует в тип boolean
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'done'
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException неверный формат параметра
     */
    public static boolean getDone(Map<String, String> mapData)
            throws InvalidParameterException, BadParameterException {
        if (!mapData.containsKey("done")
                || mapData.get("done").isEmpty()) {
            throw new BadParameterException(BadParameterException.ExceptionType.DONE);
        }
        if ("true".equalsIgnoreCase(mapData.get("done"))
                || "false".equalsIgnoreCase(mapData.get("done"))) {
            return Boolean.parseBoolean(mapData.get("done"));
        } else {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.DONE);
        }
    }

    /**
     * Метод извлекает параметр 'name', удаляет всё что не является цифрами и символами
     * и обрезает до допустимого размера NAME_MAX_SIZE
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'name'
     * @throws BadParameterException параметр отсутствует или отсутствует значение параметра
     */
    public static String getName(Map<String, String> mapData) throws BadParameterException {
        if (mapData.containsKey("name") && !mapData.get("name").isEmpty()) {
            String name = mapData.get("name").replaceAll("[^\\p{L}\\p{Z}]", "").trim();
            return name.length() < NAME_MAX_SIZE ? name : name.substring(0, NAME_MAX_SIZE);
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.NAME);
        }
    }

    /**
     * Метод извлекает параметр 'title', удаляет всё что не является цифрами и символами
     * и обрезает до допустимого размера TITLE_MAX_SIZE
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'title'
     * @throws BadParameterException параметр отсутствует или отсутствует значение параметра
     */
    public static String getTitle(Map<String, String> mapData) throws BadParameterException {
        if (mapData.containsKey("title")) {
            String title = mapData.get("title").replaceAll("[^\\p{L}\\p{Z}]", "").trim();
            return title.length() < TITLE_MAX_SIZE ? title : title.substring(0, TITLE_MAX_SIZE);
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.TITLE);
        }
    }

    /**
     * Метод извлекает параметр 'priority' и преобразует в число
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'priority'
     * @throws BadParameterException       параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException   неверный формат параметра
     * @throws OutOfRangePriorityException недопустимое значение параметра
     */
    public static byte getPriority(Map<String, String> mapData)
            throws BadParameterException, OutOfRangePriorityException, InvalidParameterException {
        if (mapData.containsKey("priority") && !mapData.get("priority").isEmpty()) {
            int priority;
            try {
                priority = Integer.parseInt(mapData.get("priority"));
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(InvalidParameterException.ExceptionType.PRIORITY);
            }
            if (priority > 0 && priority <= 5) {
                return (byte)priority;
            } else {
                throw new OutOfRangePriorityException();
            }
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.PRIORITY);
        }
    }

    /**
     * Метод извлекает параметр 'offset' и преобразует в число
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'offset'
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException неверный формат параметра
     */
    public static long getOffset(Map<String, String> mapData) throws BadParameterException, InvalidParameterException {
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
     * Метод извлекает параметр 'limit' и преобразует в число
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'limit'
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException неверный формат параметра
     */
    public static int getLimit(Map<String, String> mapData) throws BadParameterException, InvalidParameterException {
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
     * Метод извлекает параметр 'dateCreated' и преобразует в обьект LocalDateTime
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'dateCreated'
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException неверный формат параметра
     */
    public static LocalDateTime getDateCreated(Map<String, String> mapData)
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
     * Метод извлекает параметр 'dateChange' и преобразует в обьект LocalDateTime
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'dateChange'
     * @throws InvalidParameterException неверный формат параметра
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     */
    public static LocalDateTime getDateChange(Map<String, String> mapData)
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
     * Метод извлекает параметр 'dateCreatedSort' и преобразует в тип boolean
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'dateCreatedSort'
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException неверный формат параметра
     */
    public static boolean getDateCreatedSort(Map<String, String> mapData)
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
     * Метод извлекает параметр 'dateChangeSort' и преобразует в тип boolean
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'dateChangeSort'
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException неверный формат параметра
     */
    public static boolean getDateChangeSort(Map<String, String> mapData)
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
     * Метод извлекает параметр 'nameSort' и преобразует в тип boolean
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'nameSort'
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException неверный формат параметра
     */
    public static boolean getNameSort(Map<String, String> mapData)
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
     * Метод извлекает параметр 'doneSort' и преобразует в тип boolean
     *
     * @param mapData принемат обьект Map с параметрами
     * @return возвращает значение параметра 'doneSort'
     * @throws BadParameterException     параметр отсутствует или отсутствует значение параметра
     * @throws InvalidParameterException неверный формат параметра
     */
    public static boolean getDoneSort(Map<String, String> mapData)
            throws BadParameterException, InvalidParameterException {
        if (!mapData.containsKey("doneSort")
                || mapData.get("doneSort").isEmpty()) {
            throw new BadParameterException(BadParameterException.ExceptionType.DONE_SORT);
        }
        if ("true".equalsIgnoreCase(mapData.get("doneSort"))
                || "false".equalsIgnoreCase(mapData.get("doneSort"))) {
            return Boolean.parseBoolean(mapData.get("doneSort"));
        } else {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.DONE_SORT);
        }
    }
}
