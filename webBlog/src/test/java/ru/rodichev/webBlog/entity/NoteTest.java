package ru.rodichev.webBlog.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteTest {

    @Test
    void toHtmlBreakLines() {
        String actual = Note.toHtmlBreakLines("First line\nSecond line");
        String expected = "First line<br />Second line";
        assertEquals(expected, actual);
    }

    @Test
    void breaklinesToWhitespace() {
        String actual = Note.breaklinesToWhitespace("First line<br />Second line");
        String expected = "First line      Second line";
        assertEquals(expected, actual);
    }

    @Test
    void toSqlBreakLines() {
        String actual = Note.toSqlBreakLines("First line<br />Second line");
        String expected = "First line\nSecond line";
        assertEquals(expected, actual);
    }

    @Test
    void getShortText() {
        String actual = Note.getShortText("Chapter 1\n" +
                "\n" +
                "\n" +
                "\n" +
                "It was a bright cold day in April, and the clocks were striking thirteen.\n" +
                "Winston Smith, his chin nuzzled into his breast in an effort to escape the\n" +
                "vile wind, slipped quickly through the glass doors of Victory Mansions,\n" +
                "though not quickly enough to prevent a swirl of gritty dust from entering\n" +
                "along with him.\n" +
                "\n" +
                "The hallway smelt of boiled cabbage and old rag mats. At one end of it a\n" +
                "coloured poster, too large for indoor display, had been tacked to the wall.\n" +
                "It depicted simply an enormous face, more than a metre wide: the face of a\n" +
                "man of about forty-five, with a heavy black moustache and ruggedly handsome\n" +
                "features. Winston made for the stairs. It was no use trying the lift. Even\n" +
                "at the best of times it was seldom working, and at present the electric\n" +
                "current was cut off during daylight hours. It was part of the economy drive\n" +
                "in preparation for Hate Week. The flat was seven flights up, and Winston,\n" +
                "who was thirty-nine and had a varicose ulcer above his right ankle, went\n" +
                "slowly, resting several times on the way. On each landing, opposite the\n" +
                "lift-shaft, the poster with the enormous face gazed from the wall. It was\n" +
                "one of those pictures which are so contrived that the eyes follow you about\n" +
                "when you move. BIG BROTHER IS WATCHING YOU, the caption beneath it ran.\n" +
                "\n" +
                "Inside the flat a fruity voice was reading out a list of figures which had\n" +
                "something to do with the production of pig-iron. The voice came from an\n" +
                "oblong metal plaque like a dulled mirror which formed part of the surface\n" +
                "of the right-hand wall. Winston turned a switch and the voice sank\n" +
                "somewhat, though the words were still distinguishable. The instrument\n" +
                "(the telescreen, it was called) could be dimmed, but there was no way of\n" +
                "shutting it off completely. He moved over to the window: a smallish, frail\n" +
                "figure, the meagreness of his body merely emphasized by the blue overalls\n" +
                "which were the uniform of the party. His hair was very fair, his face\n" +
                "naturally sanguine, his skin roughened by coarse soap and blunt razor\n" +
                "blades and the cold of the winter that had just ended.\n" +
                "\n" +
                "Outside, even through the shut window-pane, the world looked cold. Down in\n" +
                "the street little eddies of wind were whirling dust and torn paper into\n" +
                "spirals, and though the sun was shining and the sky a harsh blue, there\n" +
                "seemed to be no colour in anything, except the posters that were plastered\n" +
                "everywhere. The black-moustachio'd face gazed down from every commanding\n" +
                "corner. There was one on the house-front immediately opposite. BIG BROTHER\n" +
                "IS WATCHING YOU, the caption said, while the dark eyes looked deep into\n" +
                "Winston's own. Down at street level another poster, torn at one corner,\n" +
                "flapped fitfully in the wind, alternately covering and uncovering the\n" +
                "single word INGSOC. In the far distance a helicopter skimmed down between\n" +
                "the roofs, hovered for an instant like a bluebottle, and darted away again\n" +
                "with a curving flight. It was the police patrol, snooping into people's\n" +
                "windows. The patrols did not matter, however. Only the Thought Police\n" +
                "mattered.");
        String expected = "Chapter 1\n" +
                "\n" +
                "\n" +
                "\n" +
                "It was a bright cold day in April, and the clocks were striking thirteen.\n" +
                "Winston Smith, his chin nuzzled into his breast in an effort to escape the\n" +
                "vile wind, slipped quickly through the glass doors of Victory Mansions,\n" +
                "though not quickly enough to prevent a swirl of gritty dust from entering\n" +
                "along with him.\n" +
                "\n" +
                "The hallway smelt of boiled cabbage and old rag mats. At one end of it a\n" +
                "coloured poster, too large for indoor display, had been tacked to the wall.\n" +
                "It depicted simply an enormous face, more than a metre wide: the face of a\n" +
                "man of about forty-five, with a heavy black moustache and ruggedly handsome\n" +
                "features. Winston made for the stairs. It was no use trying the lift. Even\n" +
                "at the best of times it was seldom working, and at present the electric\n" +
                "current was cut off during daylight hours. It was part of the economy drive\n" +
                "in preparation for Hate Week. The flat was seven flights up, and Winston,\n" +
                "who was thirty-nine and had a varicose ulcer above his right ankle,...";
        assertEquals(expected,actual);

    }

}