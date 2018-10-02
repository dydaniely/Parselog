package com.ef.Parser;

import com.ef.Parser.service.AccessLog;
import org.jline.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.FileNotFoundException;

@ShellComponent
public class CommandListener {

    public CommandListener() {
    }

    @Autowired
    private AccessLog accessLog;


    /**
     *  Parse  file to database and Prints IP address attempts based
     *  based parameter
     * @param path
     * @param startDate
     * @param duration
     * @param threshold
     * @return
     */
    @ShellMethod(key = "com.ef.Parser", value = "Import access log file to DB ,Syntax : com.ef.Parser /folderPath/ --startDate=2017-01-01.13:00:00 --duration=hourly/daily  --threshold=100")
    public String parser(@ShellOption String path,
                         @ShellOption String startDate,
                         @ShellOption String duration,
                         @ShellOption String threshold) {
        {
            try {
                String[] paths = path.split("=");
                String[] startDates = startDate.split("=");
                String[] durations = duration.split("=");
                String[] thresholds = threshold.split("=");
                if ((paths.length > 1 && startDates.length > 1 && durations.length > 1 && thresholds.length > 1)) {
                    return accessLog.logParser(paths[1], startDates[1], durations[1], Long.parseLong(thresholds[1]));
                } else {
                    return "Make sure you input the parameter correctly";
                }
            } catch (FileNotFoundException e) {
                Log.error("File not found");
                return "file not found";
            }
        }
    }


}
