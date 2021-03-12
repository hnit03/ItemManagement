/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhinh.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author PC
 */
public class Log {

    public Logger logger;

    public Log(String filename) throws SecurityException, IOException {
        File f = new File(filename);
        if (!f.exists()) {
            boolean create = f.createNewFile();
        }
        FileHandler fh = new FileHandler(filename, true);
        logger = Logger.getLogger("test");
        logger.addHandler(fh);
        SimpleFormatter sf = new SimpleFormatter();
        fh.setFormatter(sf);
    }

}
