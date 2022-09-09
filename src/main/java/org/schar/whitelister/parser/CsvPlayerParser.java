package org.schar.whitelister.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvPlayerParser implements PlayerNameParser {

    private static final String COMMA_DELIMITER = ",";
    private static final List<String> ILLEGAL_CHARACTERS = List.of("\"", ",");

    private final File file;
    private final String playerNameColumnName;

    public CsvPlayerParser(File file, String playerNameColumnName) {
        this.file = file;
        this.playerNameColumnName = playerNameColumnName;
    }

    @Override
    public List<String> getPlayerNames() {
        List<List<String>> records = parseCsv(this.file);
        List<String> playerNames = new ArrayList<>();

        for (List<String> record : records) {
            for (int i = 0; i < record.size(); i++) {
                record.set(i, cleanString(record.get(i)));
            }
        }

        int columnIndex = getIndexOfPlayerNameColumn(records);

        for (List<String> record : records) {
            playerNames.add(record.get(columnIndex));
        }

        playerNames.remove(playerNameColumnName);

        return playerNames;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String playerName : getPlayerNames()) {
            sb.append(playerName);
            sb.append("\n");
        }

        return sb.toString();
    }

    private List<List<String>> parseCsv(File file) {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    private String cleanString(String string) {
        String cleanedString = string;

        for (String illegalCharacter : ILLEGAL_CHARACTERS) {
            cleanedString = cleanedString.replaceAll(illegalCharacter, "");
        }

        return cleanedString;
    }

    private int getIndexOfPlayerNameColumn(List<List<String>> records) {
        List<String> firstRow = records.get(0);

        for (int i = 0; i < firstRow.size(); i++) {
            if (firstRow.get(i).equals(this.playerNameColumnName)) {
                return i;
            }
        }

        return 0;
    }
}
