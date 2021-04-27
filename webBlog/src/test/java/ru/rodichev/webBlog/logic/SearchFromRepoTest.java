package ru.rodichev.webBlog.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
class SearchFromRepoTest {

    @Test
    void toLike() {
        String actual = SearchFromRepo.toLike("Bill");
        String expected = "%Bill%";
        assertEquals(expected,actual);
    }

    @Test
    void parseTagsAsList() {
        List<String> actual = SearchFromRepo.parseTagsAsList("Tag1,tag2,tag3");
        List<String> expected = new ArrayList<>();
        expected.add("Tag1");
        expected.add("tag2");
        expected.add("tag3");
        assertEquals(expected, actual);
    }
}