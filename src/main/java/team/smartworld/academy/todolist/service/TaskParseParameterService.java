package team.smartworld.academy.todolist.service;

import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.exceptions.*;

import java.util.*;

/**
 * Сервис для проверки на корректность и преобразования значений параметров обьекта Task
 */
@Service
public class TaskParseParameterService {

    public static final int TITLE_MAX_SIZE = 255;

    private TaskParseParameterService() {
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
}
