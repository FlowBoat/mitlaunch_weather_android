package com.physicaloid.misc;

public class Misc {
    static public String toHexStr(byte[] b, int length) {
        StringBuilder str= new StringBuilder();
        for(int i=0; i<length; i++) {
            str.append(String.format("%02x ", b[i]));
        }
        return str.toString();
    }
}
