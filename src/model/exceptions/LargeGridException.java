package model.exceptions;

import java.util.ResourceBundle;

/**
 * Exception thrown when the user attempts to modify the model.grid to be bigger than the allowed size, as specified in the
 * properties file.
 *
 * @author Filip Mazurek
 */
public class LargeGridException extends Exception implements Alert {
    private static final String TEMP_RESOURCE_PACKAGE = "resources/properties/size-chooser";
    ResourceBundle TEMP_MY_RESOURCES = ResourceBundle.getBundle(TEMP_RESOURCE_PACKAGE);

    public LargeGridException() {
        super();
    }

    @Override
    public String getMessage() {
        return String.format(LARGE_GRID, TEMP_MY_RESOURCES.getString("maxDim"));
    }
}