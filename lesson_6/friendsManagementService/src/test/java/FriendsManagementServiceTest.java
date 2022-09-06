import account.AccountImpl;
import account.AccountRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FriendsManagementServiceTest {

    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    FriendsManagementService friendsManagementServiceMock = new FriendsManagementService(accountRepository);
    AccountImpl account = Mockito.mock(AccountImpl.class);

    @ParameterizedTest
    @ValueSource(strings = {"bangOf4@gmail.com", "iLoveJava@gmail.com"})
    void addNewFriendWithExistingAccountTest(String mail) {

        when(accountRepository.ifContainsAccount(mail)).thenReturn(true);
        when(account.ifFriendIsAlreadyFriend(mail)).thenReturn(false);

        assertEquals(StatusResponse.FRIEND_IS_ADDED, friendsManagementServiceMock.addFriend(account,mail));
    }

    @ParameterizedTest
    @ValueSource(strings = {"bangOf4@gmail.com"})
    void addNewFriendWithNotExistingAccountTest(String mail) {

        when(accountRepository.ifContainsAccount(mail)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> friendsManagementServiceMock.addFriend(any(AccountImpl.class),mail), "RuntimeException was expected");
        String actualMessage = exception.getMessage();
        String expectedMessage = "Account doesn't exist";

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @ParameterizedTest
    @CsvSource(value = {"bangOf4@gmail.com"}) // в @CsvSource может быть много различных аргументов в отличие от @ValueSource
    void addFriendThatIsAlreadyFriendTest(String mail) {

        when(accountRepository.ifContainsAccount(mail)).thenReturn(true);
        when(account.ifFriendIsAlreadyFriend(mail)).thenReturn(true);

        assertEquals(StatusResponse.FRIEND_IS_ALREADY_YOUR_FRIEND, friendsManagementServiceMock.addFriend(account,mail));
    }

}

