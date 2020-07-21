package com.silang.javabase.inject;


import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IntDef({2, 4, 6})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface TestIntDef {
}

class Test {

     private void doSth(@TestIntDef int param1,@TestIntDef int param2) {

    }

    public static void main(String[] args) {

         new Test().doSth(2,3);
         new Test().doSth(2,4);
    }

}
