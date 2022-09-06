package account;

import chat.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import photo.PhotoImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AccountImpl implements Account{

    private int id;
    private String name;
    private Gender gender;
    private String mail;
    private List<Chat> chats = new ArrayList<>();
    private List<PhotoImpl> photos = new ArrayList<>();
    private Set<String> friendsList = new HashSet<>();

    public AccountImpl(int id, String name, Gender gender, String mail) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.mail = mail;
    }

    public void addFriend(String mail) {
        friendsList.add(mail);
    }

    public boolean ifFriendIsAlreadyFriend(String mail) {
        return friendsList.contains(mail);
    }
}
