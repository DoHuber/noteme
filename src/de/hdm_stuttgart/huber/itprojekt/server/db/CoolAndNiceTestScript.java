package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

/**
 * Testskript zum Ausprobieren ob die DataMapper richtig funktionieren. Nicht
 * für die produktive Applikation gedacht.
 */
public class CoolAndNiceTestScript {

    public static void main(String[] args) throws Throwable {

        Vector<UserInfo> all = UserInfoMapper.getUserInfoMapper().getAllUserInfos();


        for (UserInfo u : all) {
            System.out.println(u.toString());
        }

    }

}
