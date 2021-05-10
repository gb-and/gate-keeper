/**
 * On the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * (c) 2020 grzegorz.bylica@gmail.com
 */

package org.gb.gate_keeper;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
 * @author grzegorz.bylica@gmail.com
 */
public class Config {

    private final String KEY_PIN="pin";
    private final String KEY_URL="url";
    private final String KEY_NAME="name";


    public String url = "https://github.com/gb-and/gate-keeper";
    public String pin = "1234";
    public String name = "Gate Keeper 6.9";

    private final static String FILE_NAME = "conf.prop";
    private final static String DEFAULT_NAME = "Gate Keeper 6.9";
    private final static String DEFAULT_URL =  "https://github.com/gb-and/gate-keeper";
    private final static String DEFAULT_PIN = "1234";
    private File fileDirectory;
    private Context ctx;


    public Config(File afileDirectory, Context aCtx) throws Exception {

        this.fileDirectory = afileDirectory;
        this.ctx = aCtx;

        File file = new File(fileDirectory, FILE_NAME);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        if (file.exists()) {
            Properties prop = new Properties();
            FileInputStream fileInputStream = ctx.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            prop.load(inputStreamReader);
            url = prop.getProperty(KEY_URL,DEFAULT_URL);
            if (url.trim().equals("")) {
                url = DEFAULT_URL;
            }
            pin = prop.getProperty(KEY_PIN, DEFAULT_PIN);
            if (pin.trim().equals("")) {
                pin = DEFAULT_PIN;
            }
            name = prop.getProperty(KEY_NAME, DEFAULT_NAME);
            if (name.trim().equals("")) {
                name = DEFAULT_NAME;
            }
        } else {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write("");
        }
    }

    public void setNewUrl(String aUrl) throws Exception {
        this.url = aUrl;
        setNew();
    }

    public void setNewPin(String aPin) throws Exception {
        this.pin = aPin;
        setNew();
    }

    public void setNewName(String aName) throws Exception {
        this.name = aName;
        setNew();
    }

    private void setNew() throws Exception {
        Properties prop = new Properties();
        prop.setProperty(KEY_PIN, this.pin);
        prop.setProperty(KEY_URL, this.url);
        prop.setProperty(KEY_NAME, this.name);
        File file = new File(fileDirectory, FILE_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        prop.store(outputStreamWriter, "");
    }

}
