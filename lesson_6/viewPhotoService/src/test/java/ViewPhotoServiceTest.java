import account.Account;
import account.AccountImpl;
import account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import photo.PhotoImpl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ViewPhotoServiceTest {

    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    ViewPhotoService serviceMakeAccount = new ViewPhotoService(accountRepository);

    @Test
    @Timeout(value = 400, unit = TimeUnit.MILLISECONDS)
    void viewExistingPhoto() {

        try {
            Method methodViewPhoto = ViewPhotoService.class
                    .getDeclaredMethod("viewPhoto", String.class, int.class);
            methodViewPhoto.setAccessible(true);

            when(accountRepository.ifPhotoExist(anyString(),anyInt())).thenReturn(true);
            when(accountRepository.getPhoto(anyString(),anyInt())).thenReturn(new PhotoImpl());

            assertNotNull(methodViewPhoto.invoke(serviceMakeAccount, anyString(),anyInt()));

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}