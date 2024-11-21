package academy.prog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonPrivateM {
    private final List<Message> list = new ArrayList<>();

    public JsonPrivateM(List<Message> sourceList, int fromIndex) {
        for (int i = fromIndex; i < sourceList.size(); i++)
            list.add(sourceList.get(i));
    }

    public List<Message> getList() {
        return Collections.unmodifiableList(list);
    }
}
