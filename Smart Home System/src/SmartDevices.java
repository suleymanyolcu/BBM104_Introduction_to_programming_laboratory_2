/**
 * This abstract class defines the properties and behavior of a smart device, such as a smart light, a smart plug, etc.
 * Each smart device has a name, a switch status (On/Off), and a switch time (when the device was switched on/off).
 * The class also provides methods for changing the device name, switching the device on/off, and plugging/unplugging the device.
 * This class is designed to be extended by concrete smart device classes that implement their specific functionality.
 */
import java.time.LocalDateTime;
import java.util.*;

public abstract class SmartDevices {
    private String name;
    private String switchStatus = "Off";
    private LocalDateTime switchTime;

    public static ArrayList<LocalDateTime> switchTimes = new ArrayList<>();


    SmartDevices(String name) {
        this.name = name;
    }

    SmartDevices(String name, String switchStatus) {
        this.name = name;
        this.switchStatus = switchStatus;
    }
    /**
     * Switches the device on or off and sets the switch time to the current time.
     * If the device is already in the requested switch status, an error message is printed.
     *
     * @param status The new switch status for the device (On/Off).
     */
    public void switchDevice(String status) {
        if (getSwitchStatus().equals(status) && status.equals("On")) {
            System.out.println("ERROR: This device is already switched on!");
        } else if (getSwitchStatus().equals(status) && status.equals("Off")) {
            System.out.println("ERROR: This device is already switched off!");
        } else {
            switchTimes.removeIf(s -> s.isBefore(SmartHome.initialTime));
            setSwitchStatus(status);
            if (getSwitchTime() != null) {
                setSwitchTime(null);
            }
        }
    }
    /**
     * Changes the name of the smart device.
     * If the old name is not found, an error message is printed.
     * If the new name is already used by another device, an error message is printed.
     *
     * @param oldName The current name of the device to be changed.
     * @param newName The new name to assign to the device.
     */
    public static void changeName(String oldName, String newName) {
        if (oldName.equals(newName)) {
            System.out.println("ERROR: Both of the names are the same, nothing changed!");
        } else if (!(SmartHome.devices.containsKey(oldName))) {
            System.out.println("ERROR: There is not such a device!");
        } else if (SmartHome.devices.containsKey(newName)) {
            System.out.println("ERROR: There is already a smart device with same name!");
        } else {
            List<Map.Entry<String, SmartDevices>> arrayList = new ArrayList<>(SmartHome.devices.entrySet());
            int index = -1;
            for(int i =0; i<arrayList.size();i++){
                if(arrayList.get(i).getKey().equals(oldName)){
                    index=i;                                    // LinkedHashMap keys are immutable,so It was impossible
                    break;                                      // change keys to new name. So I converted LinkedHashMap
                }                                               // to an Arraylist , did necessary operations then
                                                                    // converted back to LinkedHashMap.
            }
            SmartDevices tempDevice = SmartHome.devices.get(oldName);
            tempDevice.setName(newName);
            arrayList.remove(index);
            arrayList.add(index, new AbstractMap.SimpleEntry<>(newName, tempDevice));
            LinkedHashMap<String,SmartDevices> newMap = new LinkedHashMap<>();
            for(Map.Entry<String, SmartDevices> entry:arrayList){
                if(entry.getValue().equals(tempDevice)){
                    newMap.put(newName,entry.getValue());
                }
                else {
                    newMap.put(entry.getKey(),entry.getValue());
                }
            }
            SmartHome.devices.clear();
            SmartHome.devices.putAll(newMap);
        }
    }
    /**
     * Returns the switch time for the device.
     *
     * @return The LocalDateTime object representing the switch time for the device.
     */
    public LocalDateTime getSwitchTime() {
        return switchTime;
    }
    /**
     * Sets the switch time for the device.
     * If the switch time is earlier than the initial time of the smart home, it is removed from the switch times list.
     * The switch times list is sorted in ascending order after the new switch time is added.
     *
     * @param switchTime The new switch time for the device, represented as a LocalDateTime object.
     */
    public void setSwitchTime(LocalDateTime switchTime) {
        switchTimes.removeIf(s -> s.isBefore(SmartHome.initialTime)|| s.isEqual(SmartHome.initialTime));
        Collections.sort(switchTimes);
        this.switchTime = switchTime;
        Commands.switcher();
    }
    /**
     * Returns the name of the device.
     *
     * @return The name of the device.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the device.
     *
     * @param name The new name to assign to the device.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the switch status of the device.
     *
     * @return The switch status of the device (On/Off).
     */
    public String getSwitchStatus() {
        return switchStatus;
    }
    /**
     * Sets the switch status of the device.
     *
     * @param switchStatus The new switch status for the device (On/Off).
     */
    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }

    /**
     * Connects a plug to the device.
     *
     * @param s           The name of the plug to connect to the device.
     * @param parseDouble The amount of power (in watts) that the plug is using.
     */
    public void plugIn(String s, double parseDouble) {
    }
    /**
     * Disconnects a plug from the device.
     *
     * @param s The name of the plug to disconnect from the device.
     */
    public void plugOut(String s) {
    }
}
