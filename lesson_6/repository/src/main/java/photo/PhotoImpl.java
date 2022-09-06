package photo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.net.URL;

@Getter
@Setter
@RequiredArgsConstructor
public class PhotoImpl implements Photo{

    private int id;
    private URL urlOfPhoto;
    private double size;
}
