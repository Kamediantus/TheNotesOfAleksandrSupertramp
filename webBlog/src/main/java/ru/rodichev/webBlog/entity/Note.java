package ru.rodichev.webBlog.entity;


import org.hibernate.annotations.Type;
import ru.rodichev.webBlog.logic.CurrDate;

import javax.persistence.*;

@Table(name = "t_notes")
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String heading;
    @Type(type = "text")
    private String rawFullText;
    @Type(type = "text")
    private String moderateFullText;
    @Type(type = "text")
    private String finalFullText;
    private String date;
    private String tags;
    private boolean isChecked;
    private boolean isFixed;

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getRawFullText() {
        return rawFullText;
    }

    public void setRawFullText(String rawFullText) {
        this.rawFullText = rawFullText;
    }

    public String getModerateFullText() {
        return moderateFullText;
    }

    public void setModerateFullText(String moderateFullText) {
        this.moderateFullText = moderateFullText;
    }

    public String getFinalFullText() {
        return finalFullText;
    }

    public void setFinalFullText(String finalFullText) {
        this.finalFullText = finalFullText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /***
     * Create new note and set Checked and Fixed flags = false
     * later moderator will check it and Supertramp will fix if
     * @param heading String heading of note
     * @param fullText String rawFullText of note
     * @param tags String tags. Must have structure: tag1, tag2, tag3...
     */
    public Note(String heading, String fullText, String tags) {
        this.heading = heading;
        this.rawFullText = toHtmlBreakLines(fullText);
        this.tags = tags;
        this.date = CurrDate.getCurrDate();
        this.setChecked(false);
        this.setFixed(false);
    }

    /***
     * replace all \n to <br /> for correct displaying at site
     * @param text String raw text
     * @return String text that ready to display as HTML code
     */
    public static String toHtmlBreakLines(String text) {
        return text.replaceAll("\n", "<br />");
    }
    /***
     * replace all <br />  to 6 whitespaces for correct displaying at fix page. 6 because '<br />'.length == 6
     * @param text String raw text
     * @return String text that ready to display at fix page
     */
    public static String breaklinesToWhitespace(String text) {
        return text.replaceAll("<br />", "      ");
    }
    /***
     * replace all <br />  to \n  for correct displaying at db
     * @param text String raw text
     * @return String text that ready to display at db
     */
    public static String toSqlBreakLines(String text) {
        return text.replaceAll("<br />", "\n");
    }

    /***
     * get only 1000 chars for display short text at the main page or full text if it less than 1000 chars
     * @param text String raw text
     * @return String only 1000 chars for display short text at the main page or full text if it less than 1000 chars
     */
    public static String getShortText(String text) {
        if (text.length() > 1001) {
            if (text.substring(0, 1000).contains(" ")) {
                return text.substring(0, text.substring(0, 1000).lastIndexOf(" ")) + "...";
            } else return text.substring(0, 1000) + "...";
        } else return text;
    }

    /***
     * get count of mistakes
     * @return int count of mistakes
     */
    public int getCountOfMistakes() {
        return this.moderateFullText.split("\\|\\|").length;
    }

    public Note() {
    }
}

