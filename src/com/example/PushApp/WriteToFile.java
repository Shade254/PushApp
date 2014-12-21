package com.example.PushApp;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by slava on 12/21/14.
 */
public class WriteToFile {

    public void write(int max){
        try {
            File dir = new File("/storage/emulated/0/bluetooth");
            dir.mkdirs();
            File f = new File(dir, "con.txt");
            FileOutputStream stream = new FileOutputStream(f);
            if(stream!=null){
                stream.write(String.valueOf(max).getBytes());
                stream.close();
            }
        }
        catch(Exception e){

        }

    }
}
