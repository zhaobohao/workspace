package com.testng;

import org.testng.annotations.*;

public class TestNGAnnotionTest {
    @BeforeClass
    public void beforeClass() {
        System.out.println("@beforeClass");
    }

    @AfterClass
    public void afterClass() {
        System.out.println(
                "@afterClass"
        );
    }

    @BeforeGroups
    public void beforeGroups() {
        System.out.println("@BeforeGroups");
    }

    @AfterGroups
    public void afterGroups() {
        System.out.println("@AfterGroups");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("@BeforeMethod");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("@AfterMethod");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("@AfterTest");
    }

    @BeforeTest
    public void beforMethod() {
        System.out.println("@BeforeTest");
    }

    @Test
    public void runTestCase1() {
        System.out.println("testCase 1 is runing ");

    }

    @Test
    public void runTestCase2() {
        System.out.println("testCase 2 is runing ");
    }
}
