package com.GtasteriX.UserAPI.Util;

import java.util.Random;

public class ECodeUtil {
    public static int generateECode() {
        Random random = new Random();
        // Generate a random ECode in the range of 1000 to 9999
        return 1000 + random.nextInt(9000);
    }
}
