package annotations;

import javax.swing.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@SwingContainer
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    /**
     * Указывает тип используемой памяти для хранения данных
     */
    CacheType cacheType();

    /**
     * Указывает название файла, где будут храниться данные. Если название не задано, то по логике кэширующего прокси будет взято имя метода
     */
    String fileNamePrefix() default "";

    /**
     * Указывает, что файл надо сжать в zip архив
     */
    boolean zip() default false;

    /**
     * Указывает, какие аргументы метода учитывать при определении уникальности результата
     */
    Class<?>[] identityBy() default {};

    /**
     * Указывает максимальное количество элементов в списке
     */
    int listSize() default 0;
}
