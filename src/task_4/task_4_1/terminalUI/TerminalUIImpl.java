package task_4.task_4_1.terminalUI;



import task_4.task_4_1.exceptions.AccountIsLockedException;
import task_4.task_4_1.exceptions.NegativeMoneyException;
import task_4.task_4_1.exceptions.NotEnoughMoneyException;
import task_4.task_4_1.terminal.Terminal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TerminalUIImpl {

    private final Terminal terminal;

    public TerminalUIImpl(Terminal terminal) {
        this.terminal = terminal;
    }

    public void login() {

        showInfo("Введите свой ID в формате ### (например: 101)");
        Scanner scanner = new Scanner(System.in);
        try {
            int id = Integer.parseInt(scanner.next("\\d{3}"));
            if (terminal.checkIfAccountExist(id)) {
                securityCheck(id);
            }
            else {
                showInfo("Аккаунт с таким ID не существует");
                login();
            }
        } catch (InputMismatchException e) {
            showInfo("Неправильно введен ID");
            login();
        }
    }

    private void securityCheck(int id) {

        if (terminal.checkIfPasswordIsCorrect(id, readPassword())) {
            chooseAction(id);
        } else {
            try {
                terminal.noteWrongPasswordInput(id);
                showInfo("Неправильно введен пароль. Осталось попыток " + terminal.getNumberOfAttempts(id));
            } catch (AccountIsLockedException e) {
                showInfo(e.getMessage());
            }
            finally {
                securityCheck(id);
            }
        }
    }

    private String readPassword() {
        StringBuilder passwordSB = new StringBuilder();
            for (int i = 1; i <= 4; i++) {
                showInfo("Введите " + i + "-ю цифру пароля");
                readDigit(passwordSB);
            }
            return passwordSB.toString();

    }

    private void readDigit(StringBuilder passwordSB) {

        Scanner scanner = new Scanner(System.in);
        try {
            passwordSB.append(scanner.next("\\d"));
        }
        catch (InputMismatchException e) {
            showInfo("Введена не цифра. Попробуйте еще");
            readDigit(passwordSB);
        }
    }

    private void chooseAction(int id) {
        showInfo("Введите цифру: 1 – проверить состояние счета, 2 – снять деньги, 3 – положить деньги, 0 – выйти с аккаунта");
        StringBuilder numberOfAction = new StringBuilder();

        Scanner scanner = new Scanner(System.in);
        readDigit(numberOfAction);

        switch (numberOfAction.toString()) {
            case "1":
                showInfo("На вашем счету " + terminal.getMoney(id));
                chooseAction(id);
            case "2":
                showInfo("Сколько денег вы хотите снять?");
                try {
                    int moneyToTake =  Integer.parseInt(new Scanner(System.in).next("-*\\d+"));
                    terminal.takeMoney(id, moneyToTake);
                    showInfo("Вы сняли " + moneyToTake);
                }
                catch (NotEnoughMoneyException | NegativeMoneyException e) {
                    showInfo(e.getMessage());
                } catch (InputMismatchException e) {
                    showInfo("Если хотите снять деньги, то нужно ввести число. Например, 100");
                }
                finally {
                    chooseAction(id);
                }

                showInfo("Сколько денег вы хотите положить?");
                int moneyToPut =  Integer.parseInt(scanner.next("-*\\d+"));
                try {
                    terminal.putMoney(id, moneyToPut);
                    showInfo("Вы положили " + moneyToPut);
                }
                catch (NegativeMoneyException e) {
                    showInfo(e.getMessage());
                }
                catch (InputMismatchException e) {
                    showInfo("Если хотите снять деньги, то нужно ввести число. Например, 100");
                }
                finally {
                    chooseAction(id);
                }
            case "0": {
                login();
            }
            default:
                showInfo("Повторите ввод");
                chooseAction(id);
        }
    }

    public void showInfo (String info) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss >> ")) + info);
    }

}
