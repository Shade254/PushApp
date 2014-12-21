package com.example.PushApp;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by slava on 12/21/14.
 */
public class ReadFromFile {
    public int read(){
        try {
            File dir = new File ("/storage/emulated/0/bluetooth");
            dir.mkdirs();
            File f = new File(dir, "con.txt");
            String s = "";
            FileInputStream stream = new FileInputStream(f);
            if(stream != null) {
                StringBuilder build = new StringBuilder();
                int bytes;
                while ((bytes=stream.read()) != -1) {
                    build.append((char)bytes);
                }
                s = build.toString();
                return Integer.parseInt(s);
            }
            else{
                return -1;
            }
        }
        catch(Exception e){
            return -1;
        }
    }
}
