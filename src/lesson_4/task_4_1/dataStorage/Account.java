package lesson_4.task_4_1.dataStorage;

import java.time.LocalDateTime;

public class Account {

    private String name;
    private String password;
    private int money;
    private boolean accountIsBlocked;
    private int numberOfWrongInputPassword;
    private LocalDateTime dateOfLastAccountBlock;

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
        this.money = 0;
        this.accountIsBlocked = false;
        this.numberOfWrongInputPassword = 0;
    }

    public String getFirstName() {
        return name;
    }

    public void setFirstName(String firstName) {
        this.name = firstName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAccountIsBlocked() {
        return accountIsBlocked;
    }

    public void setAccountIsBlocked(boolean accountIsBlocked) {
        this.accountIsBlocked = accountIsBlocked;
    }

    public LocalDateTime getDateOfLastAccountBlock() {
        return dateOfLastAccountBlock;
    }

    public void setDateOfLastAccountBlock(LocalDateTime dateOfLastAccountBlock) {
        this.dateOfLastAccountBlock = dateOfLastAccountBlock;
    }


    public int getNumberOfWrongInputPassword() {
        return numberOfWrongInputPassword;
    }

    public void setNumberOfWrongInputPassword(int numberOfWrongInputPassword) {
        this.numberOfWrongInputPassword = numberOfWrongInputPassword;
    }
}
