package com.company;

import helper.JDBC;

public class Main {

    public static void main(String[] args) {
	// write your code here
        JDBC.openConnection();
        JDBC.closeConnection();
    }
}
