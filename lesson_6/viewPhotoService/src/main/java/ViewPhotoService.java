import account.AccountRepository;
import lombok.RequiredArgsConstructor;
import photo.Photo;
import photo.PhotoImpl;

import javax.swing.text.View;
import java.util.Arrays;

@RequiredArgsConstructor
public class ViewPhotoService {

    private final AccountRepository accountRepository;

    private Photo viewPhoto(String mail, int id)  {

        if (accountRepository.ifPhotoExist(mail,id)) {
            Photo photo = accountRepository.getPhoto(mail,id);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return photo;
        }
        else {
            return null;
        }
    }
}
