package com.log.Parser;

import com.log.Parser.service.AccessLog;
import org.jline.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@ShellComponent
public class CommandListener {

    @Autowired
    public CommandListener(AccessLog accessLog) {
        this.accessLog = accessLog;
    }

    private final AccessLog accessLog;


    /**
     * Parse  file to database and Prints IP address attempts based
     * based parameter
     *
     * @param path
     * @param startDate
     * @param duration
     * @param threshold
     * @return
     */
    @ShellMethod(key = "parse", value = "Import access log file to DB ,Syntax : com.log.Parser /folderPath/ --startDate=2017-01-01.13:00:00 --duration=hourly/daily  --threshold=100")
    public String parser(@ShellOption String path,
                         @ShellOption(defaultValue = "true") String startDate,
                         @ShellOption(defaultValue = "true") String duration,
                         @ShellOption(defaultValue = "true") String threshold) {
        {
            try {
                String[] paths = path.split("=");
                if (paths.length > 1)  {
                    return accessLog.logParser(paths[1]);
                } else {
                    return "Make sure you input the parameter correctly";
                }
            } catch (FileNotFoundException e) {
                Log.error("File not found");
                return "file not found";
            } catch (IOException e) {
                e.printStackTrace();
                return "Error on parsing";
            }
        }
    }
}
