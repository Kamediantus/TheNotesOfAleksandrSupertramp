package ru.rodichev.webBlog.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemarkTest {

    @Test
    void getPopupText() {
        Remark remark = new Remark("mistake|remark|24,31||");
        List<Remark> remarkList = new ArrayList<>();
        remarkList.add(remark);
        String actual = Remark.getPopupText("correct correct correct mistake correct", remarkList);
        String expected = "correct correct correct <span class=\"popup shagow\" onclick=\"popupFunc(0)\">mistake<span class=\"popuptext shadow\" id=\"myPopup0\">remark</span></span> correct";
        assertEquals(expected,actual);
    }

    @Test
    void getStartCords() {
        Remark remark = new Remark("mistake|remark|24,31||");
        List<Remark> remarkList = new ArrayList<>();
        remarkList.add(remark);
        List<Integer> actual = Remark.getStartCords(remarkList);
        List<Integer> expected = new ArrayList<>();
        expected.add(0,24);
        assertEquals(expected,actual);
    }

    @Test
    void getEndCords() {
        Remark remark = new Remark("mistake|remark|24,31||");
        List<Remark> remarkList = new ArrayList<>();
        remarkList.add(remark);
        List<Integer> actual = Remark.getEndCords(remarkList);
        List<Integer> expected = new ArrayList<>();
        expected.add(0,31);
        assertEquals(expected,actual);
    }

    @Test
    void getSortRemarks() {
        String rawModeratorText = "mistake1|remark1|24,31||mistake2|remark2|190,222||mistake3|remark3|35,38||mistake4|remark4|5,11||";
        List<Remark> actualList = Remark.getSortRemarks(rawModeratorText);
        int actual = actualList.get(0).getFirstCord();
        int expected = 5;
        assertEquals(expected,actual);

    }
}