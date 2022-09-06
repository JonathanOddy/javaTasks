import account.AccountImpl;
import account.AccountRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FriendsManagementService {

    private final AccountRepository accountRepository;

    public StatusResponse addFriend(AccountImpl userAccount, String mail) {

        if (accountRepository.ifContainsAccount(mail)) {
            if(!userAccount.ifFriendIsAlreadyFriend(mail)) {
                userAccount.addFriend(mail);
                return StatusResponse.FRIEND_IS_ADDED;
            }
            return StatusResponse.FRIEND_IS_ALREADY_YOUR_FRIEND;
        }
        else throw new RuntimeException("Account doesn't exist");
    }
}
