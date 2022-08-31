package task_4.task_4_1.terminal;

import task_4.task_4_1.exceptions.AccountIsLockedException;
import task_4.task_4_1.exceptions.NegativeMoneyException;
import task_4.task_4_1.exceptions.NotEnoughMoneyException;
import task_4.task_4_1.pinValidator.PinValidator;
import task_4.task_4_1.terminalServer.TerminalServer;

public class TerminalImpl implements Terminal {

    private final TerminalServer terminalServer;
    private final PinValidator pinValidator;

    public TerminalImpl(TerminalServer terminalServer, PinValidator pinValidator) {
        this.terminalServer = terminalServer;
        this.pinValidator = pinValidator;
    }

    @Override
    public boolean checkIfAccountExist(int id)  {
        return terminalServer.checkIfAccountExist(id);

    }

    @Override
    public boolean checkIfPasswordIsCorrect(int id, String inputPassword) {

        return pinValidator.checkPassword(inputPassword, terminalServer.getPassword(id));
    }

    @Override
    public void noteWrongPasswordInput(int id) throws AccountIsLockedException {
        terminalServer.noteWrongPasswordInput(id);
    }

    @Override
    public int getNumberOfAttempts(int id) {
        return terminalServer.getNumberOfAttempts(id);
    }

    @Override
    public int getMoney(int id) {
        return terminalServer.getMoney(id);
    }

    @Override
    public int takeMoney(int id, int moneyToTake) throws NotEnoughMoneyException, NegativeMoneyException {
        return terminalServer.takeMoney(id,moneyToTake);
    }

    @Override
    public void putMoney(int id, int moneyToPut) throws NegativeMoneyException {
        terminalServer.putMoney(id,moneyToPut);
    }
}

