package com.nechet.server.system;

import com.nechet.common.util.model.Vehicle;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.LinkedList;

public class Utils {
    static public String isEmptyLine(String line) {
        if (line.isEmpty()) {
            return null;
        } else {
            return line;
        }
    }
    private static String enviromentPath;
    private  static String salt = "qsdks112&@(!0Nn4oikwmn2*(*";
    private static String paper = "w2ejd&@(!hh9(@*#";
    private static String url = "jdbc:postgresql://pg:5432/studs";
    private static String login;
    private static String password;

    public static void setPassword(String password) {
        Utils.password = password;
    }

    public static void setLogin(String login) {
        Utils.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static String getLogin() {
        return login;
    }

    public static String getUrl(){
        return url;
    }
    public static String getSalt(){
        return  salt;

    }
    public static String getPaper(){
        return paper;
    }

    private static long id = 0L;
    static public Long getNewId() {
        LinkedList<Vehicle> coll = VehiclesManager.getInstance().getCollection();
        LinkedHashSet<Long> ids = new LinkedHashSet<>();
        coll.forEach(obj -> ids.add(obj.getId()));
        Random dice = new Random();
        id = dice.nextLong();
        while (ids.contains(id) | id < 0L) {
            id = dice.nextLong();
        }
        return id;
    }


    public static String getEnv() {
        return enviromentPath;
    }

    public static void setEnv(String enviromentPath) {
        Utils.enviromentPath = enviromentPath;
    }
}