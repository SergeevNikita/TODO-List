package team.smartworld.academy.todolist.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Класс добавляющий в атрибуты исключения сообщение пользователю и код ошибки
 */
@Component
public class ExtendedErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        final Throwable error = super.getError(webRequest);
        if (error instanceof TaskListException) {
            final TaskListException exception = (TaskListException)error;
            errorAttributes.put("message", exception.getMessage());
            errorAttributes.put("code", exception.getCodeException());
        }
        return errorAttributes;
    }
}