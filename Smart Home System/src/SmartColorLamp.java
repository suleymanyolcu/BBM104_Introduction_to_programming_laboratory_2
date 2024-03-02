/**
 * A subclass of SmartLamp that represents a smart color lamp.
 * The SmartColorLamp class adds the ability to control the color
 * of the lamp, in addition to the basic functionality of turning
 * the lamp on and off, setting the brightness and kelvin of the lamp,
 * and getting the lamp status. The color of the lamp can be set using
 * a color code and a brightness value. The color mode can be turned on
 * or off, and the current color of the lamp can be retrieved.
 */
public class SmartColorLamp extends SmartLamp {
    private String colorCode;
    private boolean colorMode;

    /**
     * Creates a new SmartColorLamp with the specified name, default Kelvin value of 4000 and default brightness of 100, and color mode turned off.
     *
     * @param name the name of the lamp
     */
    SmartColorLamp(String name) {
        super(name);
        setKelvin(4000);
        setBrightness(100);
        setColorMode(false);
    }
    /**
     * Creates a new SmartColorLamp with the specified name, default Kelvin value of 4000, default brightness of 100, and color mode turned off, and sets the initial status of the lamp.
     *
     * @param name the name of the lamp
     * @param initialStatus the initial status of the lamp
     */
    SmartColorLamp(String name, String initialStatus) {
        super(name, initialStatus);
        setKelvin(4000);
        setBrightness(100);
        setColorMode(false);
    }
    /**
     * Creates a new SmartColorLamp with the specified name, initial status, Kelvin value and brightness, and color mode turned off.
     *
     * @param name the name of the lamp
     * @param initialStatus the initial status of the lamp
     * @param kelvin the Kelvin value of the lamp
     * @param brightness the brightness value of the lamp
     */
    SmartColorLamp(String name, String initialStatus, int kelvin, int brightness) {
        super(name, initialStatus, kelvin, brightness);
        colorMode = false;
    }
    /**
     * Creates a new SmartColorLamp with the specified name, initial status, color code, and brightness, and color mode turned on.
     *
     * @param name the name of the lamp
     * @param initialStatus the initial status of the lamp
     * @param colorCode the color code of the lamp
     * @param brightness the brightness value of the lamp
     */
    SmartColorLamp(String name, String initialStatus, String colorCode, int brightness) {
        super(name, initialStatus, 0, brightness);
        this.colorCode = colorCode;
        colorMode = true;
    }

    public boolean getColorMode() {
        return colorMode;
    }

    public void setColorMode(boolean colorMode) {
        this.colorMode = colorMode;
    }
    /**
     * Sets the color of the lamp to the specified color code and brightness, and turns on color mode.
     *
     * @param colorCode the color code of the lamp
     * @param brightness the brightness value of the lamp
     */
    public void setColor(String colorCode, int brightness) {
        setColorCode(colorCode);
        setBrightness(brightness);
        setColorMode(true);
    }
    /**
     * Returns the color code of the lamp.
     *
     * @return the color code of the lamp
     */
    public String getColorCode() {
        return colorCode;
    }
    /**
     * Sets the color code of the lamp.
     *
     * @param colorCode the new color code of the lamp
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
        setColorMode(true);
    }
    /**
     * Returns a string representation of the lamp, including its name, switch status, color or kelvin value, brightness, and switch time.
     *
     * @return a string representation of the lamp
     */
    public String toString() {
        if (getSwitchTime() != null) {
            if (getColorMode()) {
                return String.format("Smart Color Lamp %s is %s and its color value is %s with %d%% brightness, and its time to switch its status is %s.", getName(), getSwitchStatus().toLowerCase(), getColorCode(), getBrightness(), getSwitchTime().format(SmartHome.formatter));
            } else {
                return String.format("Smart Color Lamp %s is %s and its color value is %dK with %d%% brightness, and its time to switch its status is %s.", getName(), getSwitchStatus().toLowerCase(), getKelvin(), getBrightness(), getSwitchTime().format(SmartHome.formatter));
            }
        } else {
            if (getColorMode()) {
                return String.format("Smart Color Lamp %s is %s and its color value is %s with %d%% brightness, and its time to switch its status is null.", getName(), getSwitchStatus().toLowerCase(), getColorCode(), getBrightness());
            } else {
                return String.format("Smart Color Lamp %s is %s and its color value is %dK with %d%% brightness, and its time to switch its status is null.", getName(), getSwitchStatus().toLowerCase(), getKelvin(), getBrightness());
            }
        }
    }
}
