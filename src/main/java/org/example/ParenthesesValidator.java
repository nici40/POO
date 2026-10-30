package org.example;

public class ParenthesesValidator {

    private final Logger logger = new Logger("Parentheses");

    public boolean isValid(String input) {
        int open = 0; // how many ( are still waiting for their )

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '(') {
                open++;
                logger.trace("Opening parenthesis found at position " + i);
            } else if (c == ')') {
                // ) with nothing to close, no point checking the rest
                if (open == 0) {
                    logger.warn("Closing parenthesis at position " + i + " has no opening parenthesis to match");
                    return finish(input, false,
                            "the closing parenthesis at position " + i + " has no matching opening parenthesis");
                }
                open--;
                logger.info("Matching pair found, closed at position " + i);
            }
            // anything else we just ignore
        }

        // some ( never got closed
        if (open > 0) {
            String reason = (open == 1)
                    ? "1 opening parenthesis is never closed"
                    : open + " opening parentheses are never closed";
            return finish(input, false, reason);
        }

        return finish(input, true, null);
    }

    // prints the result (and the error if its bad) and gives the result back
    private boolean finish(String input, boolean valid, String reason) {
        logger.info("Result for \"" + input + "\": " + (valid ? "valid" : "invalid"));
        if (!valid) {
            logger.error("Input \"" + input + "\" is invalid: " + reason);
        }
        return valid;
    }
}