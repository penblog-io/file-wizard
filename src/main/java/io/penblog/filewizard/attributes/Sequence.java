package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.IllegalAttributeValueException;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Generate sequential a number or letter based on provided attribute values
 * attribute: {sequence:options}
 */
public class Sequence implements AttributeGeneratorInterface {

    private final static String NUMBER = "number";
    private final static String LETTER = "letter";

    /**
     * @param attributeValue Attribute value has 4 parts:
     *                       part 1: type, either sequencing by number or letter
     *                       part 2: start from (number or letter, depending on type)
     *                       part 3: interval, how many numbers characters to skip, negative number is allowed.
     *                       positive = increment
     *                       negative = decrement
     *                       part 4: group limit, how many items per group
     *                       part 5: mask, mask should be longer than the sequences. for example:
     *                       mask: 00000
     *                       sequence number is: 10
     *                       result: 00010
     */
    @Override
    public String generate(Item item, String attributeValue) {
        String value = "";
        List<String> parts = Arrays.stream(attributeValue.split(",")).map(String::trim).toList();
        String type = parts.get(0);

        int interval = parts.size() >= 3 && parts.get(2) != null && isNumeric(parts.get(2)) ? Integer.parseInt(parts.get(2)) : 1;
        int limit = parts.size() >= 4 && parts.get(3) != null ? Integer.parseInt(parts.get(3)) : 1;
        String mask = parts.size() >= 5 && parts.get(4) != null ? parts.get(4) : "";

        // re-adjust index so that it will produce the same number/letter within the group
        int index = item.getIndex() / limit;
        int position = index * interval;

        if (NUMBER.equals(type)) {
            int start = Integer.parseInt(parts.get(1));
            value = getSequentNumber(position, start, mask);
        } else if (LETTER.equals(type)) {
            value = getSequentLetters(position, parts.get(1), mask);
        }
        return value;
    }

    /**
     * validate 5 options within the attribute value
     *          part 1: a number or a letter
     *          part 2: a number or a letter
     *          part 3: a number
     *          part 4: a number
     *          part 5: a string
     */
    @Override
    public void validateAttributeValue(String attributeValue) throws IllegalAttributeValueException {

        List<String> parts = Arrays.stream(attributeValue.split(",")).map(String::trim).toList();
        boolean isValid = parts.size() >= 2 && parts.size() <= 5;

        if (isValid) {
            if (NUMBER.equals(parts.get(0))) {
                if (!isNumeric(parts.get(1))) isValid = false;
            } else if (LETTER.equals(parts.get(0))) {
                if (!isLetters(parts.get(1))) isValid = false;
            } else isValid = false;
        }

        if (isValid) {
            if (parts.size() >= 3 && !isNumeric(parts.get(2))) isValid = false;
        }
        if (isValid) {
            if (parts.size() >= 4 && !isNumeric(parts.get(3))) isValid = false;
        }
        if (!isValid) throw new IllegalAttributeValueException();
    }


    /**
     * Generate number sequences
     */
    private String getSequentNumber(int position, int start, String mask) {

        int sequenceInt = start + position;
        if (sequenceInt < 0) sequenceInt = 0;
        String sequence = String.valueOf(sequenceInt);
        if (mask.length() > sequence.length()) {
            sequence = mask.substring(0, mask.length() - sequence.length()) + sequence;
        }
        return sequence;
    }

    /**
     * Generate letter sequences
     */
    private String getSequentLetters(int position, String start, String mask) {
        boolean isLowerCase = isLowerCase(start.charAt(0));
        char[] chars = start.toLowerCase().toCharArray();
        StringBuilder sequenceBuilder = new StringBuilder();

        int startInt = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            startInt += Math.pow(26, i) * ((int) chars[i] - 96);
        }

        int sequenceInt = startInt + position;

        while (sequenceInt > 0) {
            int charInt = (sequenceInt - 1) % 26;
            sequenceBuilder.insert(0, (char) (charInt + 97));
            sequenceInt = (sequenceInt - charInt) / 26;
        }

        String sequence = sequenceBuilder.toString();
        if (sequence.isEmpty()) sequence = "a";
        if (!isLowerCase) sequence = sequence.toUpperCase();
        if (mask.length() > sequence.length()) {
            sequence = mask.substring(0, mask.length() - sequence.length()) + sequence;
        }

        return sequence;
    }


    /**
     * check if a string is all numbers
     */
    private boolean isNumeric(String str) {
        if (str == null) return false;
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * check if a string is all letters
     */
    private boolean isLetters(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        return pattern.matcher(str).matches();
    }

    /**
     * check if a character is lower case
     */
    private boolean isLowerCase(char c) {
        return (int) c >= 97 && (int) c <= 122;
    }

}
