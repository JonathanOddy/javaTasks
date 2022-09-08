package task_2.classLoaders;

import java.io.*;

/**
 * Данный класслоадер умеет загружать классы из файлов дешифрую их. Ваша задача
 * переопределить метод findClass().В нем лоадер считывает зашифрованный массив байт,
 * дешифрует его и превращает в класс (с помощью метода defineClass).
 * <p>
 * На вход класслодер принимает ключ шифрования, рутовую папку, в которой будет искать классы,
 * родительский класслодер. Логика шифрования/дешифрования с использованием ключа
 * может быть любой на ваш вкус (например, каждый считаный байт класса увеличить на определение число).
 */
public class EncryptedPlusKeyClassLoader extends ClassLoader {

    private final int key;
    private final File dir;

    public EncryptedPlusKeyClassLoader(int key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        File file = new File(dir.getAbsolutePath() + "/" + name + ".class");
        if (!file.isFile()) {
            throw new ClassNotFoundException("В директории " + dir.getAbsolutePath() + " нет файла с именем " + name);
        }
        try {
            BufferedInputStream bufferedInputStream =
                    new BufferedInputStream(new FileInputStream(file));
            byte[] data = new byte[(int) file.length()];
            int resultOfRead = bufferedInputStream.read(data);
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (data[i] - key);
            }
            bufferedInputStream.close();
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
