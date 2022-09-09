package org.schar.whitelister.parser;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class SimpleTextPlayerParser implements PlayerNameParser {

    private final File file;

    public SimpleTextPlayerParser(File file) {
        this.file = file;
    }

    @Override
    public List<String> getPlayerNames() {
        return Arrays.stream(this.toString().split("\n")).toList();
    }

    @Override
    public String toString() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
