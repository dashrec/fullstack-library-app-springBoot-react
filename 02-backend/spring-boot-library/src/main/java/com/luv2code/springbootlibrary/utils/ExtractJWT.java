package com.luv2code.springbootlibrary.utils;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {
    public static String payloadJWTExtraction(String token, String extraction) {

        token.replace("Bearer ", ""); // we remove bearer with empty space to have just a token
        String[] chunks = token.split("\\."); // split it at the periods  --> header.payload.signature and get array of 3 elements
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1])); // decode payload
        String[] entries = payload.split(","); // inside of payload each element is separated by comas and we need to split

        Map<String, String> map = new HashMap<String, String>(); // create map because we want a key value pair
        for (String entry : entries) {
            String[] keyValue = entry.split(":"); //
            if (keyValue[0].equals(extraction)) { // find the value of sub which is gonna be email

                int remove = 1;
                if (keyValue[1].endsWith("}")) { // make sure we don't have brackets
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);  //and add just a value which is going to be email
            }
        }
        if (map.containsKey(extraction)) {
            return map.get(extraction); // return email
        }
        return null;
    }
}