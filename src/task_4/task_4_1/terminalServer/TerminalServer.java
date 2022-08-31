package task_4.task_4_1.terminalServer;


import task_4.task_4_1.exceptions.AccountIsLockedException;
import task_4.task_4_1.exceptions.NegativeMoneyException;
import task_4.task_4_1.exceptions.NotEnoughMoneyException;

public interface TerminalServer {

    void blockAccount(int id) throws AccountIsLockedException;

    boolean checkIfAccountExist(int id) ;

    void noteWrongPasswordInput(int id) throws AccountIsLockedException;

    boolean checkIfAccountIsBlocked(int id) throws AccountIsLockedException;

    int getNumberOfAttempts(int id);

    int getMoney(int id);

    int takeMoney(int id, int moneyToTake) throws NotEnoughMoneyException, NegativeMoneyException;

    void putMoney(int id, int moneyToPut) throws NegativeMoneyException;

    String getPassword(int id);
}
