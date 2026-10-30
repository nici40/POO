package org.example;

public class Main {

    public static void main(String[] args) {
        ParenthesesValidator validator = new ParenthesesValidator();

        // the 4 tests from the handout, with everything visible
        section("Required tests, level = TRACE");
        Logger.setLevel(Logger.LogLevel.TRACE);
        test(validator, "()", true);
        test(validator, ")((()))", false);
        test(validator, "(", false);
        test(validator, "(())((()())())", true);

        // my own tests, on INFO the trace stuff should disappear but results stay the same
        section("Extra tests, level = INFO");
        Logger.setLevel(Logger.LogLevel.INFO);
        test(validator, "(())", true);
        test(validator, "(()", false);
        test(validator, "())", false);
        test(validator, "", true);

        // on ERROR only the errors should show up
        section("Level = ERROR");
        Logger.setLevel(Logger.LogLevel.ERROR);
        test(validator, "()", true);
        test(validator, "(()", false);

        // proof that level is static, i set it once and both loggers listen
        section("Static level is shared by all loggers");
        Logger first = new Logger("First");
        Logger second = new Logger("Second");
        Logger.setLevel(Logger.LogLevel.WARN);
        first.info("not printed");
        second.info("not printed");
        first.warn("printed");
        second.warn("printed");

        // the formats from the table in 3.2
        section("Formats from section 3.2");
        Logger.setLevel(Logger.LogLevel.TRACE);
        Logger demo = new Logger("Parentheses");
        String[] formats = {"[%l] %m", "%n: %m", "[%t] %m", "%l - %n - %m"};
        for (String f : formats) {
            Logger.setFormat(f);
            demo.info("Valid input");
        }

        // extra point, %r gives a random message for each level
        section("Extra credit: %r");
        Logger.setFormat("[%l] %n: %r");
        demo.trace("");
        demo.info("");
        demo.warn("");
        demo.error("");

        Logger.setFormat("[%t] [%l] %n: %m"); // put the default back
    }

    // runs one test and says PASS or FAIL
    private static void test(ParenthesesValidator validator, String input, boolean expected) {
        boolean actual = validator.isValid(input);
        String status = (actual == expected) ? "PASS" : "FAIL";
        System.out.println(status + ": \"" + input + "\" -> " + actual + " (expected " + expected + ")");
        System.out.println();
    }

    private static void section(String title) {
        System.out.println("===== " + title + " =====");
    }
}