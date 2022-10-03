package serializationUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationUtil {

    public synchronized static void serialize(Result result, File file) throws IOException {

        if (file.exists()) {
            ObjectOutputStream oos = new AppendingObjectOutputStream(new FileOutputStream(file, true));
            oos.writeObject(result);
        }
        else {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(result);
        }
    }

    public synchronized static <T> List<T> deserialize(File file) throws IOException, ClassNotFoundException {
        List<T> listOfResults = new ArrayList<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

        while (true) {
            try {
                T result = (T) ois.readObject();
                listOfResults.add(result);
            } catch (EOFException e) {
                break;
            }
        }
        return listOfResults;
    }
}
