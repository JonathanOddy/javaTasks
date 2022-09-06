import account.Account;
import account.AccountImpl;
import account.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import account.AccountRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RegistrationServiceTest {

    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    RegistrationService serviceMakeAccount = new RegistrationService(accountRepository);

    @ParameterizedTest
    @MethodSource("provideAccountsWithNewMail")
    void testRegistrationWithNewMail(String name, Gender gender, String mail) {
        when(accountRepository.ifContainsAccount(mail)).thenReturn(getAccountMap().containsKey(mail));
        assertEquals(StatusResponse.ACCOUNT_IS_CREATED, serviceMakeAccount.makeAccount(name,gender,mail));
    }

    @Test
    void testRegistrationWithSameMail() {
        String mail = getAccountMap().keySet().stream().findFirst().get();
        when(accountRepository.ifContainsAccount(mail)).thenReturn(getAccountMap().containsKey(mail));
        assertEquals(StatusResponse.ACCOUNT_WITH_THIS_EMAIL_IS_EXISTING,
                serviceMakeAccount.makeAccount("Jonathan", Gender.MALE, mail));
    }

    private Map<String, Account> getAccountMap() {
        String mail = "bangOf4@gmail.com";
        return new HashMap<>() {
            {
                put(mail, new AccountImpl(1, "Alex", Gender.MALE, mail));
            }
        };
    }

    private static Stream<Arguments> provideAccountsWithNewMail() {
        return Stream.of(
                Arguments.of("Alex", Gender.MALE, "codingInMyLife@gmail.com"),
                Arguments.of("Joana", Gender.FEMALE, "helloJava@gmail.com")
        );
    }
}