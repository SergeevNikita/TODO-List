package team.smartworld.academy.todolist.service;

import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.exceptions.*;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.UUID;

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
     * Метод получает параметр 'done' и преобразует в тип boolean
     *
     * @param doneParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'done'
     * @throws InvalidParameterException неверный формат параметра
     */
    public static boolean isDone(String doneParam)
            throws InvalidParameterException {

        if ("true".equalsIgnoreCase(doneParam)
                || "false".equalsIgnoreCase(doneParam)) {
            return Boolean.parseBoolean(doneParam);
        } else {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.DONE);
        }
    }

    /**
     * Метод получает параметр 'name', удаляет всё что не является цифрами и символами
     * и обрезает до допустимого размера NAME_MAX_SIZE
     *
     * @param nameParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'name'
     */
    public static String parseName(String nameParam) {
        String name = nameParam.replaceAll("[^\\p{javaAlphabetic}\\p{Digit}\\p{Space}]+", "").trim();
        return name.length() < NAME_MAX_SIZE ? name : name.substring(0, NAME_MAX_SIZE);
    }

    /**
     * Метод получает параметр 'title', удаляет всё что не является цифрами и символами
     * и обрезает до допустимого размера TITLE_MAX_SIZE
     *
     * @param titleParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'title'
     */
    public static String parseTitle(String titleParam) {
        String title = titleParam.replaceAll("[^\\p{javaAlphabetic}\\p{Digit}\\p{Space}]+", "").trim();
        return title.length() <= TITLE_MAX_SIZE ? title : title.substring(0, TITLE_MAX_SIZE);
    }

    /**
     * Метод получает параметр 'priority' и преобразует в число
     *
     * @param priorityParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'priority'
     * @throws InvalidParameterException   неверный формат параметра
     * @throws OutOfRangePriorityException недопустимое значение параметра
     */
    public static byte parsePriority(String priorityParam)
            throws OutOfRangePriorityException, InvalidParameterException {
        int priority;
        try {
            priority = Integer.parseInt(priorityParam);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.PRIORITY);
        }
        if (priority > 0 && priority <= 5) {
            return (byte)priority;
        } else {
            throw new OutOfRangePriorityException();
        }
    }

    /**
     * Метод получает параметр 'page' и преобразует в число
     *
     * @param pageParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'offset'
     * @throws InvalidParameterException неверный формат параметра
     */
    public static int parsePage(String pageParam) throws InvalidParameterException {

        try {
            int page = Integer.parseInt(pageParam);
            return Math.max(page, 0);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.PAGE);
        }
    }

    /**
     * Метод получает параметр 'limit' и преобразует в число
     *
     * @param limitParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'limit'
     * @throws InvalidParameterException неверный формат параметра
     */
    public static int parseLimit(String limitParam) throws InvalidParameterException {
        try {
            int limit = Integer.parseInt(limitParam);
            return limit < 1 || limit > 100 ? 10 : limit;
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.LIMIT);
        }
    }

    /**
     * Метод получает параметр 'dateCreated' и преобразует в обьект LocalDateTime
     *
     * @param dateCreatedParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'dateCreated'
     * @throws InvalidParameterException неверный формат параметра
     */
    public static LocalDateTime parseDateCreated(String dateCreatedParam)
            throws InvalidParameterException {
        try {
            return LocalDateTime.parse(dateCreatedParam, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.DATE_CREATED);
        }
    }

    /**
     * Метод получает параметр 'dateChange' и преобразует в обьект LocalDateTime
     *
     * @param dateChangeParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'dateChange'
     * @throws InvalidParameterException неверный формат параметра
     */
    public static LocalDateTime parseDateChange(String dateChangeParam)
            throws InvalidParameterException {
        try {
            return LocalDateTime.parse(dateChangeParam, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException(InvalidParameterException.ExceptionType.DATE_CHANGE);
        }
    }

    /**
     * Метод получает параметр 'sortBy' и возвращает его значение
     *
     * @param sortByParam принемат обьект String с параметроми
     * @return возвращает значение параметра 'sortBy'
     */
    public static String parseSortBy(String sortByParam) {
        switch (sortByParam) {
            case "dateCreated":
                return "dateCreated";
            case "dateChange":
                return "dateChange";
            case "done":
                return "done";
            case "name":
                return "name";
            default:
                return "id";
        }
    }
}
