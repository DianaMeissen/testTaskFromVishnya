package com.vishnya;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FindTag tryToFind = new FindTag();
        tryToFind.findObject(args[0], args[1]);
    }
}
