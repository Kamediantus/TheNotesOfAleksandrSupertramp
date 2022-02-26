//package ru.rodichev.webBlog.session;
//
//public final class UserSession {
//
//    private static UserSession instance;
//
//    private String userName;
//    private String role;
//    private String key;
//
//    private UserSession(String userName, String role) {
//        this.userName = userName;
//        this.role = role;
//    }
//
//    public static UserSession getInstace(String userName, String role) {
//        if(instance == null) {
//            instance = new UserSession(userName, role);
//        }
//        return instance;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public String role () {
//        return role;
//    }
//
//    public void cleanUserSession() {
//        userName = "";// or null
//        role = "";// or null
//    }
//
//    @Override
//    public String toString() {
//        return "UserSession{" +
//                "userName='" + userName + '\'' +
//                ", role=" + role +
//                '}';
//    }
//}