package account;


import account.Account;
import lombok.NoArgsConstructor;
import photo.PhotoImpl;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class AccountRepository {

    private final Map<String, AccountImpl> accountMap = new HashMap<>();

    public Map<String, AccountImpl> getAll() {
        return accountMap;
    };

    public PhotoImpl getPhoto(String userMail, int photoId) { return accountMap.get(userMail).getPhotos().get(photoId);}

    public boolean ifPhotoExist(String userMail, int photoId) {return accountMap.get(userMail).getPhotos().contains(photoId);}

    public void save(AccountImpl account, String mail) {
        accountMap.put(mail, account);
    }

    public boolean ifContainsAccount(String mail) {
        return accountMap.containsKey(mail);
    }
}
