package lesson_4.task_4_1.pinValidator;

public class PinValidator {

    public boolean checkPassword(String inputPassword, String realPassword) {
        return inputPassword.equals(realPassword);
    }
}
