package logic;


import ru.rodichev.webBlog.logic.Remark;

import java.util.ArrayList;
import java.util.List;

public class TestNote {
    public static void main(String[] args) {
        Remark remark = new Remark("mistake|remark|24,31||");
        List<Remark> remarkList = new ArrayList<>();
        remarkList.add(remark);
        String popupStr = Remark.getPopupText("correct correct correct mistake correct", remarkList);
        System.out.println(popupStr);
    }

}
