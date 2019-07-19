package com.guava.baseutils;

import com.google.common.base.Preconditions;
import org.testng.annotations.Test;


public class PreconditionsTest {
    @Test
    public static void preconditionTest()
    {

        Preconditions.checkArgument(true,"this is not right for function");
        Preconditions.checkElementIndex(5,20);
        Preconditions.checkState(false,"we can do it better!!");
        Preconditions.checkPositionIndex(5,20);
    }

}
