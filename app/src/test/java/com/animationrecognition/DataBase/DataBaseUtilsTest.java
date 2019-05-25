package com.animationrecognition.DataBase;

import org.junit.Test;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotEquals;

public class DataBaseUtilsTest {

    @Test
    public void getuserNameTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field userName = c.getDeclaredField("userName");
        userName.setAccessible(true);
        userName.set(dbu, null);
        assertNull(dbu.getuserName(), null);
    }

    @Test
    public void getuserNameTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field userName = c.getDeclaredField("userName");
        userName.setAccessible(true);
        userName.set(dbu, "x");
        assertNotEquals(dbu.getuserName(), null);
    }

    @Test
    public void getemailTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field email = c.getDeclaredField("email");
        email.setAccessible(true);
        email.set(dbu, null);
        assertNull(dbu.getemail(), null);
    }

    @Test
    public void getemailTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field email = c.getDeclaredField("email");
        email.setAccessible(true);
        email.set(dbu, "x");
        assertNotEquals(dbu.getemail(), null);
    }

    @Test
    public void getupdateStatusTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field updateStatus = c.getDeclaredField("updateStatus");
        updateStatus.setAccessible(true);
        updateStatus.set(dbu, null);
        assertNull(dbu.getUpdateStatus(), null);
    }

    @Test
    public void getupdateStatusTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field updateStatus = c.getDeclaredField("updateStatus");
        updateStatus.setAccessible(true);
        updateStatus.set(dbu, "x");
        assertNotEquals(dbu.getUpdateStatus(), null);
    }

    @Test
    public void getloginStatusTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field loginStatus = c.getDeclaredField("loginStatus");
        loginStatus.setAccessible(true);
        loginStatus.set(dbu, null);
        assertNull(dbu.getLoginStatus(), null);
    }

    @Test
    public void getloginStatusTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field loginStatus = c.getDeclaredField("loginStatus");
        loginStatus.setAccessible(true);
        loginStatus.set(dbu, "x");
        assertNotEquals(dbu.getLoginStatus(), null);
    }

    @Test
    public void getregisterStatusTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field registerStatus = c.getDeclaredField("registerStatus");
        registerStatus.setAccessible(true);
        registerStatus.set(dbu, null);
        assertNull(dbu.getRegisterStatus(), null);
    }

    @Test
    public void getregisterStatusTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field registerStatus = c.getDeclaredField("registerStatus");
        registerStatus.setAccessible(true);
        registerStatus.set(dbu, "x");
        assertNotEquals(dbu.getRegisterStatus(), null);
    }

    @Test
    public void getwikiStatusTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field wikiStatus = c.getDeclaredField("wikiStatus");
        wikiStatus.setAccessible(true);
        wikiStatus.set(dbu, null);
        assertNull(dbu.getWikiStatus());
    }

    @Test
    public void getwikiStatusTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field wikiStatus = c.getDeclaredField("wikiStatus");
        wikiStatus.setAccessible(true);
        wikiStatus.set(dbu, null);
        assertNull(dbu.getWikiStatus());
    }

    @Test
    public void setemailTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field email = c.getDeclaredField("email");
        email.setAccessible(true);
        email.set(dbu, null);
        assertNull(email.get(dbu));
    }

    @Test
    public void setemailTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field email = c.getDeclaredField("email");
        email.setAccessible(true);
        email.set(dbu, "x");
        assertNotEquals(email.get(dbu), null);
    }

    @Test
    public void setuserNameTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field userName = c.getDeclaredField("userName");
        userName.setAccessible(true);
        userName.set(dbu, null);
        assertNull(userName.get(dbu));
    }

    @Test
    public void setuserNameTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field userName = c.getDeclaredField("userName");
        userName.setAccessible(true);
        userName.set(dbu, "x");
        assertNotEquals(userName.get(dbu), null);
    }

    @Test
    public void setpasswordTestSuccess() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field password = c.getDeclaredField("password");
        password.setAccessible(true);
        password.set(dbu, null);
        assertNull(password.get(dbu));
    }

    @Test
    public void setpasswordTestFail() throws NoSuchFieldException, IllegalAccessException {
        DataBaseUtils dbu = new DataBaseUtils();
        Class c = DataBaseUtils.class;
        Field password = c.getDeclaredField("password");
        password.setAccessible(true);
        password.set(dbu, "x");
        assertNotEquals(password.get(dbu), null);
    }
}

