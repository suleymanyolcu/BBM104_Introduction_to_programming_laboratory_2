/**
 The Commands class provides static methods for performing various smart home commands,
 including setting the initial time, setting the time, skipping minutes, switching devices,
 performing a no-operation (nop), and generating a zReport.
 */
import java.time.LocalDateTime;
import java.util.*;

public class Commands {
    /**
     * Sets the initial time of the smart home.
     * @param time the time to set, in the format of the SmartHome formatter.
     */
    public static void setInitialTime(String time) {
        SmartHome.initialTime = LocalDateTime.parse(time, SmartHome.formatter);
    }
    /**
     * Sets the time of the smart home and switches devices accordingly.
     * @param time the time to set, in the format of the SmartHome formatter.
     */

    public static void setTime(String time) {
        SmartHome.initialTime = LocalDateTime.parse(time, SmartHome.formatter);
        switcher();
    }
    /**
     * Skips a specified number of minutes and switches devices accordingly.
     * @param minutes the number of minutes to skip.
     */
    public static void skipMinutes(String minutes) {
        SmartHome.initialTime = SmartHome.initialTime.plusMinutes(Integer.parseInt(minutes));
        switcher();
    }
    /**
     * Switches devices on or off according to their scheduled switch times.
     */
    public static void switcher() {
        SmartDevices.switchTimes.removeIf(s -> s.isBefore(SmartHome.initialTime) || s.isEqual(SmartHome.initialTime));
        for (SmartDevices device : SmartHome.devices.values()) {
            if (device.getSwitchTime() != null) {
                if (device.getSwitchTime().isBefore(SmartHome.initialTime) || device.getSwitchTime().isEqual(SmartHome.initialTime)) {
                    SmartHome.nullList.remove(device);
                    SmartHome.nullList.add(0,device);
                    SmartHome.switchTimesList.remove(device);
                    if (device.getSwitchStatus().equals("Off")) {
                        device.switchDevice("On");
                    } else {
                        device.switchDevice("Off");
                    }
                }
            }
        }
    }
    /**
     * Performs a no-operation (nop) command, switching devices to the next scheduled switch time.
     */
    public static void nop() {
        SmartDevices.switchTimes.removeIf(s -> s.isBefore(SmartHome.initialTime) || s.isEqual(SmartHome.initialTime));
        if (SmartDevices.switchTimes.size() == 0) {
            System.out.println("ERROR: There is nothing to switch!");
        } else {
            SmartHome.initialTime = SmartDevices.switchTimes.get(0);
            switcher();
        }
    }
    /**
     * Generates a zReport of all devices, sorted by their scheduled switch times.
     */
    public static void zReport() {
        Comparator<SmartDevices> smartDevicesComparator = Comparator.comparing(SmartDevices::getSwitchTime);
        for(SmartDevices device: SmartHome.devices.values()){
            if(device.getSwitchTime()!=null&&!SmartHome.switchTimesList.contains(device)){
                SmartHome.switchTimesList.add(device);
                SmartHome.nullList.remove(device);
            }
            else if(device.getSwitchTime()==null&&!SmartHome.nullList.contains(device)) {
                SmartHome.nullList.add(device);
            }
        }
        SmartHome.switchTimesList.sort(smartDevicesComparator);
        for (int i=0;i<SmartHome.switchTimesList.size();i++){
            System.out.println(SmartHome.switchTimesList.get(i));
        }
        for(int j=0;j<SmartHome.nullList.size();j++){
            System.out.println(SmartHome.nullList.get(j));
        }
    }
    /**
     Checks if a device with the given name already exists in the Smart Home.
     @param name the name of the device to be checked
     @return true if the device does not exist, false otherwise
     */
    public static boolean isSameNameDoesntExist(String name) {
        if (!(SmartHome.devices.containsKey(name))) {
            return true;
        } else {
            System.out.println("ERROR: There is already a smart device with same name!");
            return false;
        }
    }
    /**
     Checks if a string is a valid hexadecimal integer starting with 0x or 0X.
     @param str the string to be checked
     @return true if the string is a valid hexadecimal integer, false otherwise
     */
    public static boolean isInteger(String str) {
        return !((str.charAt(0) != '0') || (str.charAt(1) != 'x' && str.charAt(1) != 'X'));
    }
    /**
     Checks if a string is a valid initial status for a Smart Home device (On or Off).
     @param str the string to be checked
     @return true if the string is a valid initial status, false otherwise
     */
    public static boolean isInitialStatus(String str) {
        if (str.equals("On") || str.equals("Off")) {
            return true;
        } else {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
    }
    /**
     Checks if a string is a valid minute value (a positive integer greater than 0).
     @param str the string to be checked
     @return true if the string is a valid minute value, false otherwise
     */
    public static boolean isMinute(String str) {
        if (str == null) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        try {
            int temp = Integer.parseInt(str);
            if (temp < 0) {
                System.out.println("ERROR: Time cannot be reversed!");
                return false;
            } else if (temp == 0) {
                System.out.println("ERROR: There is nothing to skip!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        return true;
    }
    /**
     Checks if a string is a valid brightness value (a positive integer between 0 and 100 inclusive).
     @param str the string to be checked
     @return true if the string is a valid brightness value, false otherwise
     */
    public static boolean isBrightness(String str) {
        if (str == null) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        try {
            int temp = Integer.parseInt(str);
            if (!(temp >= 0 && temp <= 100)) {
                System.out.println("ERROR: Brightness must be in range of 0%-100%!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        return true;

    }
    /**
    Checks if a string is a valid hexadecimal color code (a string starting with 0x or 0X, followed by a hexadecimal number between 0x0 and 0xFFFFFF).
    @param str the string to be checked
    @return true if the string is a valid color code, false otherwise
    */
    public static boolean isColored(String str) {

        if (str == null || str.length() < 2 || str.charAt(0) != '0' || (str.charAt(1) != 'x' && str.charAt(1) != 'X')) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        String hex = str.substring(2);
        try {
            int val = Integer.parseInt(hex, 16);
            if (!(val >= 0x0 && val <= 0xFFFFFF)) {
                System.out.println("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
                return false;
            }
        } catch (NumberFormatException ex) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        return true;
    }
    /**
     Validates a string input as a Kelvin temperature value.
     @param str the input string to be validated
     @return true if the input is a valid Kelvin temperature value in the range of 2000K-6500K, false otherwise
     */
    public static boolean isKelvin(String str) {
        if (str == null) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        try {
            int temp = Integer.parseInt(str);
            if (!(temp >= 2000 && temp <= 6500)) {
                System.out.println("ERROR: Kelvin value must be in range of 2000K-6500K!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        return true;
    }
    /**
     Validates a string input as an Ampere current value.
     @param str the input string to be validated
     @return true if the input is a valid positive Ampere current value, false otherwise
     */
    public static boolean isAmpere(String str) {
        if (str == null) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        try {
            double temp = Double.parseDouble(str);
            if ((temp <= 0)) {
                System.out.println("ERROR: Ampere value must be a positive number!");
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }

    }
    /**
     Validates a string input as a Megabyte data size value.
     @param str the input string to be validated
     @return true if the input is a valid positive Megabyte data size value, false otherwise
     */
    public static boolean isMegabyte(String str) {
        if (str == null) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        try {
            double temp = Double.parseDouble(str);
            if ((temp <= 0)) {
                System.out.println("ERROR: Megabyte value must be a positive number!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        return true;

    }
}
