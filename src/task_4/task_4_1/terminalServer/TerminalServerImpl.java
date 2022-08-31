package task_4.task_4_1.terminalServer;

import task_4.task_4_1.dataStorage.Account;
import task_4.task_4_1.dataStorage.AccountStorage;
import task_4.task_4_1.exceptions.AccountIsLockedException;
import task_4.task_4_1.exceptions.NegativeMoneyException;
import task_4.task_4_1.exceptions.NotEnoughMoneyException;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TerminalServerImpl implements TerminalServer {

    private final Map<Integer,Account> accountMap = AccountStorage.getAccountMap();
    private final int NUMBER_OF_ATTEMPTS = 3;
    private final int TIME_OF_LOCK_DURATION = 10;

    @Override
    public boolean checkIfAccountExist(int id) {
        return accountMap.containsKey(id);

    }

    @Override
    public void noteWrongPasswordInput(int id) throws AccountIsLockedException {

        accountMap.get(id).setNumberOfWrongInputPassword(accountMap.get(id).getNumberOfWrongInputPassword()+1);
        if (!checkIfAccountIsBlocked(id) && accountMap.get(id).getNumberOfWrongInputPassword() >= NUMBER_OF_ATTEMPTS) {
            blockAccount(id);
        }

    }

    @Override
    public boolean checkIfAccountIsBlocked(int id) throws AccountIsLockedException {

        if (accountMap.get(id).getAccountIsBlocked()) {
            LocalDateTime dateOfLastAccountBlock = accountMap.get(id).getDateOfLastAccountBlock();
            LocalDateTime dateTimeNow = LocalDateTime.now();
            if (Duration.between(dateOfLastAccountBlock, dateTimeNow).getSeconds() >= TIME_OF_LOCK_DURATION) {
                accountMap.get(id).setAccountIsBlocked(false);
                accountMap.get(id).setNumberOfWrongInputPassword(0);
            }
            else {
                throw new AccountIsLockedException("Превышен лимит ввода пароля. Аккаунт заблокирован до "
                        + dateOfLastAccountBlock.plusSeconds(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss")));
            }
        }
        return accountMap.get(id).getAccountIsBlocked();
    }

    @Override
    public int getNumberOfAttempts(int id) {
        return NUMBER_OF_ATTEMPTS  - accountMap.get(id).getNumberOfWrongInputPassword();
    }

    @Override
    public int getMoney(int id) {
        return accountMap.get(id).getMoney();
    }

    @Override
    public int takeMoney(int id, int moneyToTake) throws NotEnoughMoneyException, NegativeMoneyException {

        if (moneyToTake <= accountMap.get(id).getMoney()) {
            if (moneyToTake > 0) {
                accountMap.get(id).setMoney(accountMap.get(id).getMoney() - moneyToTake);
                return accountMap.get(id).getMoney();
            }
            else throw new NegativeMoneyException("Сумма не может быть отрицательной или нулевой");
        }
        else throw new NotEnoughMoneyException("На счету недостаточно средств");
    }

    @Override
    public void putMoney(int id, int moneyToPut) throws NegativeMoneyException {
        if (moneyToPut > 0) {
            accountMap.get(id).setMoney(accountMap.get(id).getMoney() + moneyToPut);
        }
        else throw new NegativeMoneyException("Сумма не может быть отрицательной или нулевой");
    }

    @Override
    public String getPassword(int id) {
        return accountMap.get(id).getPassword();
    }


    @Override
    public void blockAccount(int id) throws AccountIsLockedException {
        accountMap.get(id).setAccountIsBlocked(true);
        LocalDateTime dateOfLastAccountBlock = LocalDateTime.now();
        accountMap.get(id).setDateOfLastAccountBlock(dateOfLastAccountBlock);
        throw new AccountIsLockedException("Превышен лимит ввода пароля. Аккаунт заблокирован до "
                +  dateOfLastAccountBlock.plusSeconds(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss")));
    }

}
