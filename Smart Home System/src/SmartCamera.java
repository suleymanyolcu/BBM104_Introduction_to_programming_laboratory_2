/**
 * A SmartCamera is a type of SmartDevices that is capable of recording video and storing it in a memory card.
 * The amount of storage used by the SmartCamera is calculated based on the duration the camera was turned on.
 * The SmartCamera can be turned on and off using the setSwitchStatus() method inherited from the SmartDevices class.
 * The SmartCamera can also be initialized with a specified initial status, which can be "On" or "Off".
 * The SmartCamera keeps track of the time it was switched on and off in order to calculate the amount of storage used.
 *
 */
import java.time.Duration;
import java.time.LocalDateTime;

public class SmartCamera extends SmartDevices {
    private double mbps;
    public LocalDateTime switchCamOn;
    public LocalDateTime switchCamOf;
    // ...

    /**
     * Constructs a SmartCamera with the specified name and Mbps.
     * The SmartCamera is initialized with a default status of "Off".
     *
     * @param name The name of the SmartCamera.
     * @param mbps The Mbps (megabits per second) of the SmartCamera.
     */
    SmartCamera(String name, double mbps) {
        super(name);
        this.mbps = mbps;
    }
    /**
     * Constructs a SmartCamera with the specified name, Mbps, and initial status.
     * If the initial status is "On", the SmartCamera's switchCamOn attribute is set to the current time.
     *
     * @param name The name of the SmartCamera.
     * @param mbps The Mbps (megabits per second) of the SmartCamera.
     * @param initialStatus The initial status of the SmartCamera.
     */
    SmartCamera(String name, double mbps, String initialStatus) {
        super(name, initialStatus);
        this.mbps = mbps;
        if(initialStatus.equals("On")){
            switchCamOn=SmartHome.initialTime;
        }
    }
    /**
     * Returns the amount of storage used by the SmartCamera.
     * The amount of storage used is calculated by multiplying the duration the camera was on (in minutes) by the Mbps.
     *
     * @return The amount of storage used by the SmartCamera.
     */
    public double getStorage() {
        if(switchCamOf!=null){
            Duration duration = Duration.between(switchCamOn,switchCamOf);
            return mbps*(duration.toMinutes());
        }
        else {
            return 0.00;
        }

    }
    /**
     * Sets the switch status of the SmartCamera and updates the switchCamOn or switchCamOff attributes as necessary.
     * If the switch status is "On", the switchCamOn attribute is set to the current time.
     * If the switch status is "Off", the switchCamOff attribute is set to the current time.
     * @param switchStatus The new switch status of the SmartCamera.
     */
    @Override
    public void setSwitchStatus(String switchStatus) {
        super.setSwitchStatus(switchStatus);
        if(switchStatus.equals("On")){
            switchCamOn=SmartHome.initialTime;
        }
        else if(switchStatus.equals("Off")){
            switchCamOf=SmartHome.initialTime;
        }
    }
    /**
     * Returns a String representation of the SmartCamera object.
     * The String includes the name of the SmartCamera, its current status,
     * the amount of storage used so far (excluding current status),
     * and the time at which its status will be switched next.
     * @return A String representation of the SmartCamera object.
     */
    @Override
    public String toString() {
        if (getSwitchTime() != null) {
            return String.format("Smart Camera %s is %s and used %.2f MB of storage so far (excluding current status), and its time to switch its status is %s.", getName(), getSwitchStatus().toLowerCase(),getStorage(), getSwitchTime().format(SmartHome.formatter));
        } else {
            return String.format("Smart Camera %s is %s and used %.2f MB of storage so far (excluding current status), and its time to switch its status is null.", getName(), getSwitchStatus().toLowerCase(),getStorage());
        }

    }

}
