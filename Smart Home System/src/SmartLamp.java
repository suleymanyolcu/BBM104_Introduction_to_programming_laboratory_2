/**
 * The SmartLamp class represents a smart lamp device that extends the SmartDevices class.
 * It has properties such as kelvin and brightness, and methods for setting and getting them.
 * It also has a method for setting the kelvin and brightness values for the lamp to produce white light.
 */
public class SmartLamp extends SmartDevices {
    private int kelvin;
    private int brightness;

    /**
     * Constructs a new SmartLamp object with the given name, and initializes its kelvin and brightness values to default values.
     * @param name the name of the lamp
     */
    SmartLamp(String name) {
        super(name);
        setKelvin(4000);
        setBrightness(100);
    }
    /**
     * Constructs a new SmartLamp object with the given name and initial status, and initializes its kelvin and brightness values to default values.
     * @param name the name of the lamp
     * @param initialStatus the initial status of the lamp
     */
    SmartLamp(String name, String initialStatus) {
        super(name, initialStatus);
        setKelvin(4000);
        setBrightness(100);
    }
    /**
     * Constructs a new SmartLamp object with the given name, initial status, kelvin, and brightness values.
     * @param name the name of the lamp
     * @param initialStatus the initial status of the lamp
     * @param kelvin the initial kelvin value of the lamp
     * @param brightness the initial brightness value of the lamp
     */
    SmartLamp(String name, String initialStatus, int kelvin, int brightness) {
        super(name, initialStatus);
        this.kelvin = kelvin;
        this.brightness = brightness;
    }
    /**
     * Sets the kelvin and brightness values of the lamp to produce white light.
     * @param kelvin the kelvin value for the white light
     * @param brightness the brightness value for the white light
     */
    public void setWhite(String kelvin, String brightness) {
        int tempKelvin = Integer.parseInt(kelvin);
        int tempBrightness = Integer.parseInt(brightness);
        setKelvin(tempKelvin);
        setBrightness(tempBrightness);
    }
    /**
     * Gets the kelvin value of the lamp.
     *
     * @return the kelvin value of the lamp
     */
    public int getKelvin() {
        return kelvin;
    }
    /**
     * Sets the kelvin value of the lamp.
     *
     * @param kelvin the new kelvin value for the lamp
     */
    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }
    /**
     * Gets the brightness value of the lamp.
     *
     * @return the brightness value of the lamp
     */
    public int getBrightness() {
        return brightness;
    }
    /**
     * Sets the brightness value of the lamp.
     *
     * @param brightness the new brightness value for the lamp
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
    /**
     * Returns a string representation of the lamp object, including its name, status, kelvin, brightness, and switch time (if applicable).
     *
     * @return a string representation of the lamp object
     */
    public String toString() {
        if (getSwitchTime() != null) {
            return String.format("Smart Lamp %s is %s and its kelvin value is %dK with %d%% brightness, and its time to switch its status is %s.", getName(), getSwitchStatus().toLowerCase(), getKelvin(), getBrightness(), getSwitchTime().format(SmartHome.formatter));
        } else {
            return String.format("Smart Lamp %s is %s and its kelvin value is %dK with %d%% brightness, and its time to switch its status is null.", getName(), getSwitchStatus().toLowerCase(), getKelvin(), getBrightness());
        }

    }
}
