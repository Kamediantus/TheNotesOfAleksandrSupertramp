package ru.rodichev.webBlog.logic;

import ru.rodichev.webBlog.entity.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/***
 * represents an Remark
 */
public class Remark implements Comparable<Remark> {

    private String mistake;
    private String remark;
    private int[] cords = new int[2];
    private String popupView;

    /***
     * create new Remark from raw string from db
     * @param rawRemark raw string for one remark
     */
    public Remark(String rawRemark) {
        String[] elements = rawRemark.split("\\|");
        this.mistake = elements[0];
        this.remark = elements[1];
        this.cords[0] = Integer.parseInt(elements[2].split(",")[0]);
        this.cords[1] = Integer.parseInt(elements[2].split(",")[1]);
    }

    /***
     * create text with popups from raw text and List of Remarks. Needs to display mistakes as popups
     * @param fullText raw full text from db
     * @param remarksList  List of Remarks
     * @see Remark#getSortRemarks(String) to get sorted list of Remarks
     * @return String full text with necessary tags and classes
     */
    public static String getPopupText(String fullText, List<Remark> remarksList) {
        List<Integer> startCords = Remark.getStartCords(remarksList);
        List<Integer> endCords = Remark.getEndCords(remarksList);
        System.out.println(fullText.length());
        System.out.println(Note.breaklinesToWhitespace(fullText).length());
        String popupText = fullText.replaceAll("<br />", "      ");
        System.out.println(popupText);
        String mistake;
        for (int i = remarksList.size() - 1; i >= 0; i--) {
            mistake = remarksList.get(i).getMistake();
            popupText = popupText.substring(0, startCords.get(i)) +
                    "<span class=\"popup shagow\" onclick=\"popupFunc(" + i + ")\">" +
                    mistake + "<span class=\"popuptext shadow\" id=\"myPopup" + i +
                    "\">" + remarksList.get(i).getRemark() + "</span></span>" + popupText.substring(endCords.get(i));
        }
        return popupText;
    }

    /***
     * get List of start cords of mistakes
     * @param remarkList List of Remarks
     * @see Remark#getSortRemarks(String) to create List of Remarks
     * @return List of start cords as Ints
     */
    public static List<Integer> getStartCords(List<Remark> remarkList) {
        List<Integer> endCords = new ArrayList<>();
        for (int i = 0; i < remarkList.size(); i++) {
            endCords.add(remarkList.get(i).getFirstCord());
        }
        return endCords;
    }

    /***
     * get List of end cords of mistakes
     * @param remarkList List of Remarks
     * @see Remark#getSortRemarks(String) to create List of Remarks
     * @return List of end cords as Ints
     */
    public static List<Integer> getEndCords(List<Remark> remarkList) {
        List<Integer> endCords = new ArrayList<>();
        for (int i = 0; i < remarkList.size(); i++) {
            endCords.add(remarkList.get(i).getLastCord());
        }
        return endCords;
    }

    /***
     * create sorted List of Remarks from raw Moderators text from db
     * @param moderatorsText String moderators text from db
     * @return  List of remarks sorted by start cords
     */
    public static List<Remark> getSortRemarks(String moderatorsText) {
        TreeSet<Remark> remarks = new TreeSet<>();
        String[] rawRemarks = moderatorsText.split("\\|\\|");
//        for (int i = 0; i < rawRemarks.length; i++) {
//            Remark remark = new Remark(rawRemarks[i]);
//            remarks.add(remark);
//        }
        for(String el : rawRemarks){
            Remark remark = new Remark(el);
            remarks.add(remark);
        }
        List<Remark> remarkList = new ArrayList<>();
        for (Remark el : remarks) {
            remarkList.add(el);
        }
        return remarkList;
    }

    public int getFirstCord() {
        return this.getCords()[0];
    }

    public int getLastCord() {
        return this.getCords()[1];
    }

    public String getMistake() {
        return mistake;
    }

    public void setMistake(String mistake) {
        this.mistake = mistake;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int[] getCords() {
        return cords;
    }

    public void setCords(int[] cords) {
        this.cords = cords;
    }

    public String getPopupView() {
        return popupView;
    }

    public void setPopupView(String popupView) {
        this.popupView = popupView;
    }

    /***
     * comparator for sort Remarks by start cords
     * @param o
     * @return
     */
    @Override
    public int compareTo(Remark o) {
        return this.getFirstCord() - o.getFirstCord();
    }
}
