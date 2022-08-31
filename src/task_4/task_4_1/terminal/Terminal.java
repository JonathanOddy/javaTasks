package task_4.task_4_1.terminal;


import task_4.task_4_1.exceptions.AccountIsLockedException;
import task_4.task_4_1.exceptions.NegativeMoneyException;
import task_4.task_4_1.exceptions.NotEnoughMoneyException;

public interface Terminal {

    boolean checkIfAccountExist(int id);

    boolean checkIfPasswordIsCorrect(int id, String password);

    void noteWrongPasswordInput(int id) throws AccountIsLockedException;

    int getNumberOfAttempts(int id);

    int getMoney(int id);

    int takeMoney(int id, int parseInt) throws NotEnoughMoneyException, NegativeMoneyException;

    void putMoney(int id, int parseInt) throws NegativeMoneyException;
}
