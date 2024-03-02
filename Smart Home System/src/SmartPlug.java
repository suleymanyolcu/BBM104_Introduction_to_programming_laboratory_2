/**
 This class represents a smart plug device that extends the abstract class SmartDevices. It has a name,
 amperage, total energy consumed, switch status and energy consumption start and end time. It allows to plug a device,
 unplug a device and calculate the energy consumed by the device.
 */
import java.time.Duration;
import java.time.LocalDateTime;

public class SmartPlug extends SmartDevices {
    private double ampere;
    final double VOLTAGE = 220;
    private boolean plugged;
    public LocalDateTime energyStart;
    public LocalDateTime energyEnd;
    private double totalEnergy;
    /**
     * Constructor for creating a SmartPlug with just a name.
     * @param name the name of the SmartPlug
     */
    SmartPlug(String name) {
        super(name);
    }
    /**
     * Constructor for creating a SmartPlug with a name and initial status.
     * @param name the name of the SmartPlug
     * @param initialStatus the initial status of the SmartPlug ("On" or "Off")
     */
    SmartPlug(String name, String initialStatus) {
        super(name, initialStatus);
    }
    /**
     * Constructor for creating a SmartPlug with a name, initial status, and amperage.
     * @param name the name of the SmartPlug
     * @param initialStatus the initial status of the SmartPlug ("On" or "Off")
     * @param ampere the amperage of the device that will be plugged in
     */
    SmartPlug(String name, String initialStatus, double ampere) {
        super(name, initialStatus);
        this.ampere = ampere;
        setPlugged(true);
        if(initialStatus.equals("On")&&ampere!=0){
            energyStart=SmartHome.initialTime;
        }
    }
    /**
     * Method for plugging a device into the SmartPlug.
     * @param name the name of the device to be plugged in
     * @param amper the amperage of the device to be plugged in
     */
    public void plugIn(String name, double amper) {
        if (!(SmartHome.devices.containsKey(name))) {
            System.out.println("ERROR: There is not such a device!");
        } else if (!(SmartHome.devices.get(name) instanceof SmartPlug)) {
            System.out.println("ERROR: This device is not a smart plug!");
        } else if (plugged) {
            System.out.println("ERROR: There is already an item plugged in to that plug!");
        } else {
            plugged = true;
            ampere = amper;
            if (getSwitchStatus().equals("On") && (amper != 0)) {
                energyStart = SmartHome.initialTime;
                getConsumption();
            }
        }
    }
    /**
     * Method for unplugging a device from the SmartPlug.
     * @param name the name of the device to be unplugged
     */
    public void plugOut(String name) {
        if (!(SmartHome.devices.containsKey(name))) {
            System.out.println("ERROR: There is not such a device!");
        } else if (!(SmartHome.devices.get(name) instanceof SmartPlug)) {
            System.out.println("This device is not a smart plug!");
        } else if (!plugged) {
            System.out.println("ERROR: This plug has no item to plug out from that plug!");
        } else {
            if (getSwitchStatus().equals("On")) {
                plugged = false;
                energyEnd = SmartHome.initialTime;
                getConsumption();
            }
            else {
                plugged = false;
            }
        }
    }
    /**
     * Returns whether the smart plug is currently plugged.
     * @return true if the plug is currently plugged, false otherwise.
     */
    public boolean isPlugged() {
        return plugged;
    }
    /**
     * Sets the status of whether the smart plug is currently plugged.
     * @param plugged true if the plug is currently plugged, false otherwise.
     */
    public void setPlugged(boolean plugged) {
        this.plugged = plugged;
    }
    /**
     * Calculates the energy consumption of the smart plug based on the start and end time of usage,
     * and adds it to the total energy consumed so far.
     */
    public void getConsumption() {
        if ((energyEnd != null && energyStart != null)) {
            if (energyEnd.isAfter(energyStart)) {
                Duration duration = Duration.between(energyStart, energyEnd);
                totalEnergy = totalEnergy + (VOLTAGE * ampere * ((duration.toMinutes()) / 60.0));
            }
        }
    }
    /**
     * Returns the total energy consumed by the smart plug so far.
     * @return the total energy consumed in kWh.
     */
    public double getTotalEnergy() {
        return totalEnergy;
    }
    /**
     * Sets the status of the smart plug based on the switch status.
     * If the switch status is "On" and the plug is plugged and current flow is not 0, it sets the start time of energy consumption.
     * If the switch status is "Off" and the start time of energy consumption is not null, it sets the end time of energy consumption and calculates the consumption.
     * @param switchStatus the switch status of the smart plug.
     */
    public void setSwitchStatus(String switchStatus) {
        super.setSwitchStatus(switchStatus);
        if (switchStatus.equals("On")) {
            if (isPlugged() && (ampere != 0)) {
                energyStart = SmartHome.initialTime;
            }
        } else if (switchStatus.equals("Off")) {
            if (energyStart != null) {
                energyEnd = SmartHome.initialTime;
                getConsumption();
            }
        }
    }
    /**
     * Returns a string representation of the smart plug device.
     * If the switch time is not null, it includes the time to switch the status.
     * Otherwise, it shows that the time to switch the status is null.
     * @return a string representation of the smart plug device.
     */
    public String toString() {
        if (getSwitchTime() != null) {
            return String.format("Smart Plug %s is %s and consumed %.2fW so far (excluding current device), and its time to switch its status is %s.", getName(), getSwitchStatus().toLowerCase(), getTotalEnergy(), getSwitchTime().format(SmartHome.formatter));
        } else {
            return String.format("Smart Plug %s is %s and consumed %.2fW so far (excluding current device), and its time to switch its status is null.", getName(), getSwitchStatus().toLowerCase(), getTotalEnergy());
        }
    }
}
