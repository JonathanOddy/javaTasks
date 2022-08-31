package task_4.task_4_1;


import task_4.task_4_1.pinValidator.PinValidator;
import task_4.task_4_1.terminal.Terminal;
import task_4.task_4_1.terminal.TerminalImpl;
import task_4.task_4_1.terminalServer.TerminalServer;
import task_4.task_4_1.terminalServer.TerminalServerImpl;
import task_4.task_4_1.terminalUI.TerminalUIImpl;

public class Main {


    public static void main(String[] args) {

        TerminalServer terminalServer = new TerminalServerImpl();
        PinValidator pinValidator= new PinValidator();
        Terminal terminal = new TerminalImpl(terminalServer,pinValidator);
        TerminalUIImpl terminalUI = new TerminalUIImpl(terminal);

        terminalUI.login();
    }

}
