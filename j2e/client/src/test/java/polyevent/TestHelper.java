package polyevent;

import java.util.Random;

public abstract class TestHelper {
    private String randomString(final int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public String generateEmail(){
        String beginEmail = randomString(5);
        String midEmail = randomString(5);
        String endEmail = randomString(3);
        return  beginEmail + "@" + midEmail + "." + endEmail;
    }
}
