import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Obfuscator {
    private static final Set<String> keyWords = new HashSet<>(Arrays.asList("String", "public", "int","class","static", "void","System"
    , "out", "print", "println", "return", "private", "import", "util", "Iterator", "NoSuchElementException", "new","Object", "this"
    ,"implements", "java", "hasNext", "boolean", "i", "throw", "throws", "if", "null","else","else if","for","arraycopy","true"
    ,"false", "continue","break","next","UnsupportedOperationException"));

    private static final Pattern stringLiteral = Pattern.compile("\"(.*?)\"");

    private static int identifierCounter = 1;
    private static final Map<String, String> identifierMap = new HashMap<>();

    public static void main(String[] args) {
        String sourceFile = "/Users/nikmenshikov/Documents/учеба/java_projects/algorithms/TaskObfuscator/src/Deque.java";
        String outputFile = "/Users/nikmenshikov/Documents/учеба/java_projects/algorithms/TaskObfuscator/src/v1.java";

        String sourceCode = readFromFile(sourceFile);
        String obfuscatedCode = obfuscateCode(sourceCode);
        writeToFile(obfuscatedCode, outputFile);

        System.out.println("Код успешно обфусцирован");
    }

    private static String readFromFile(String filePath) {
        StringBuilder code = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                code.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code.toString();
    }

    private static void writeToFile(String code, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String obfuscateCode(String code) {
        // Обработка строчных литералов
        List<String> stringLiterals = new ArrayList<>();
        Matcher literalMatcher = stringLiteral.matcher(code);
        while (literalMatcher.find()) {
            String literal = literalMatcher.group();
            stringLiterals.add(literal);
            code = code.replace(literal, "literal" + stringLiterals.size());
        }

        // Замена идентификаторов
        Matcher identifierMatcher = Pattern.compile("\\b(?!main\\b|\\b" + String.join("\\b|\\b", keyWords) + "\\b)(?!\\d)\\w+\\b").matcher(code);
        while (identifierMatcher.find()) {
            String identifier = identifierMatcher.group();
            if (identifier.charAt(0) == 'l'){
                continue;
            }
                String obfuscatedIdentifier = identifierMap.get(identifier);
                if (obfuscatedIdentifier == null) {
                    obfuscatedIdentifier = "v" + identifierCounter++;
                    identifierMap.put(identifier, obfuscatedIdentifier);
                }
                code = code.replaceAll("\\b" + identifier + "\\b", obfuscatedIdentifier);
        }
        // Восстановление строчных литералов
        for (int i = 0; i < stringLiterals.size(); i++) {
            code = code.replace("literal" + (i + 1), stringLiterals.get(i));
        }
        return code;
    }
}