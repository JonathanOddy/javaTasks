import account.AccountImpl;
import account.AccountRepository;
import account.Gender;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class RegistrationService {

    private final AccountRepository accountRepository;
    private static int countId = 0;

   public StatusResponse makeAccount(String name, Gender gender, String mail) {
       if (!accountRepository.ifContainsAccount(mail)) {
           countId++;
           accountRepository.save(new AccountImpl(countId, name, gender, mail),mail);
           return StatusResponse.ACCOUNT_IS_CREATED;
       }
       else return StatusResponse.ACCOUNT_WITH_THIS_EMAIL_IS_EXISTING;
   }
}