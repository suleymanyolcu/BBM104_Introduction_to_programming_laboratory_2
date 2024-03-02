/**
 *The SmartHome class represents a smart home system, which can control various SmartDevices.
 *It includes static variables for the initial time, a DateTimeFormatter, a Map to store the devices,
 *and two Lists to store switch times and null values.
 *The class also has a SmartHomeController method, which takes an input file, reads it, and executes
 *the commands in the file to control the smart devices.
 */
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDateTime;

public class SmartHome {
    public static LocalDateTime initialTime;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    public static Map<String, SmartDevices> devices = new LinkedHashMap<>();
    public static List<SmartDevices> switchTimesList = new ArrayList<>();
    public static List<SmartDevices> nullList = new ArrayList<>();
    /**
     The SmartHomeController method takes an input file, reads it, and executes the commands in the file to
     control the smart devices. It checks if the first command is to set the initial time, and terminates the program
     if it is not. It then executes the remaining commands in the input file and controls the smart devices
     based on the commands.
     @param input a String representing the file path of the input file
     */
    public static void SmartHomeController(String input) {
        String[] inputFile = FileInput.readFile(input, true, true);
        ArrayList<ArrayList<String>> inputs = new ArrayList<>();
        for (String s : Objects.requireNonNull(inputFile)) {
            String[] temp1 = s.split("\t");
            ArrayList<String> temp2 = new ArrayList<>(Arrays.asList(temp1));
            inputs.add(temp2);
        }
        if (!(inputs.get(0).size() == 2 && inputs.get(0).get(0).equals("SetInitialTime"))) {
            System.out.println("COMMAND: " + String.join("\t", inputs.get(0)));
            System.out.println("ERROR: First command must be set initial time! Program is going to terminate!");
            System.exit(0);
        }
        for (ArrayList<String> command : inputs) {
            System.out.println("COMMAND: " + String.join("\t", command));
            switch (command.get(0)) {
                case "SetInitialTime":
                    if (inputs.indexOf(command) == 0) {
                        try {
                            Commands.setInitialTime(command.get(1));
                            System.out.println("SUCCESS: Time has been set to "+command.get(1)+"!");
                        } catch (DateTimeParseException e) {
                            System.out.println("ERROR: Format of the initial date is wrong! Program is going to terminate!");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("ERROR: Erroneous command!");
                    }
                    break;
                case "SetTime":
                    try {
                        if (LocalDateTime.parse(command.get(1), formatter).isBefore(initialTime)) {
                            System.out.println("ERROR: Time cannot be reversed!");
                            break;
                        }
                        if (LocalDateTime.parse(command.get(1), formatter).isEqual(initialTime)) {
                            System.out.println("ERROR: There is nothing to change!");
                            break;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("ERROR: Time format is not correct!");
                        break;
                    }
                    Commands.setTime(command.get(1));
                    break;
                case "SkipMinutes":
                    if (command.size() >= 3) {
                        System.out.println("ERROR: Erroneous command!");
                    } else if (Commands.isMinute(command.get(1))) {
                        Commands.skipMinutes(command.get(1));
                    }
                    break;
                case "Nop":
                    Commands.nop();
                    break;
                case "ZReport":
                    System.out.println("Time is:\t" + initialTime.format(formatter));
                    Commands.zReport();
                    break;
                case "Add":
                    switch (command.get(1)) {
                        case "SmartPlug":
                            switch (command.size()) {
                                case 3:
                                    if(Commands.isSameNameDoesntExist(command.get(2))){
                                        devices.put(command.get(2), new SmartPlug(command.get(2)));
                                    }
                                    break;
                                case 4:
                                    if (Commands.isSameNameDoesntExist(command.get(2))&&Commands.isInitialStatus(command.get(3))) {
                                        devices.put(command.get(2), new SmartPlug(command.get(2), command.get(3)));
                                    }
                                    break;
                                case 5:
                                    if (Commands.isSameNameDoesntExist(command.get(2))&&Commands.isInitialStatus(command.get(3)) && Commands.isAmpere(command.get(4))) {
                                        devices.put(command.get(2), new SmartPlug(command.get(2), command.get(3), Double.parseDouble(command.get(4))));
                                    }
                                    break;
                                default:
                                    System.out.println("ERROR: Erroneous command!");
                                    break;
                            }
                            break;
                        case "SmartCamera":
                            switch (command.size()) {
                                case 4:
                                    if (Commands.isSameNameDoesntExist(command.get(2))&&Commands.isMegabyte(command.get(3))) {
                                        devices.put(command.get(2), new SmartCamera(command.get(2), Double.parseDouble(command.get(3))));
                                    }
                                    break;
                                case 5:
                                    if (Commands.isSameNameDoesntExist(command.get(2))&&Commands.isMegabyte(command.get(3))) {
                                        devices.put(command.get(2), new SmartCamera(command.get(2), Double.parseDouble(command.get(3)), command.get(4)));
                                    }
                                    break;
                                default:
                                    System.out.println("ERROR: Erroneous command!");
                                    break;
                            }
                            break;
                        case "SmartLamp":
                            switch (command.size()) {
                                case 3:
                                    if(Commands.isSameNameDoesntExist(command.get(2))){
                                        devices.put(command.get(2), new SmartLamp(command.get(2)));
                                    }
                                    break;
                                case 4:
                                    if (Commands.isSameNameDoesntExist(command.get(2))&&Commands.isInitialStatus(command.get(3))) {
                                        devices.put(command.get(2), new SmartLamp(command.get(2), command.get(3)));
                                    }
                                    break;
                                case 6:
                                    if (Commands.isSameNameDoesntExist(command.get(2))&&Commands.isKelvin(command.get(4)) && Commands.isBrightness(command.get(5))) {
                                        devices.put(command.get(2), new SmartLamp(command.get(2), command.get(3), Integer.parseInt(command.get(4)), Integer.parseInt(command.get(5))));
                                    }
                                    break;
                                default:
                                    System.out.println("ERROR: Erroneous command!");
                                    break;
                            }
                            break;
                        case "SmartColorLamp":
                            switch (command.size()) {
                                case 3:
                                    if(Commands.isSameNameDoesntExist(command.get(2))){
                                        devices.put(command.get(2), new SmartColorLamp(command.get(2)));
                                    }
                                    break;
                                case 4:
                                    if (Commands.isSameNameDoesntExist(command.get(2))) {
                                        devices.put(command.get(2), new SmartColorLamp(command.get(2), command.get(3)));
                                    }
                                    break;
                                case 6:
                                    if (Commands.isSameNameDoesntExist(command.get(2))&&!(Commands.isInteger(command.get(4)))) {
                                        if (Commands.isKelvin(command.get(4))) {
                                            devices.put(command.get(2), new SmartColorLamp(command.get(2), command.get(3), Integer.parseInt(command.get(4)), Integer.parseInt(command.get(5))));
                                            ((SmartColorLamp) devices.get(command.get(2))).setColorMode(false);
                                        }
                                        break;
                                    } else if (Commands.isSameNameDoesntExist(command.get(2))&&Commands.isInteger(command.get(4))) {
                                        if (Commands.isColored(command.get(4))) {
                                            devices.put(command.get(2), new SmartColorLamp(command.get(2), command.get(3), command.get(4), Integer.parseInt(command.get(5))));
                                            ((SmartColorLamp) devices.get(command.get(2))).setColorMode(true);
                                        }
                                        break;
                                    } else {
                                        System.out.println("ERROR: Erroneous command!");
                                    }
                                    break;
                                default:
                                    System.out.println("ERROR: Erroneous command!");
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case "Remove":
                    if(command.size()!=2){
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    }
                    if (!(SmartHome.devices.containsKey(command.get(1)))) {
                        System.out.println("ERROR: There is not such a device!");
                        break;
                    } else {
                        System.out.println("SUCCESS: Information about removed smart device is as follows:");
                        devices.get(command.get(1)).setSwitchStatus("Off");
                        switchTimesList.remove(devices.get(command.get(1)));
                        nullList.remove(devices.get(command.get(1)));
                        System.out.println(devices.get(command.get(1)));
                        devices.remove(command.get(1));
                    }
                    break;
                case "SetSwitchTime":
                    if(command.size()!=3){
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    }
                    if (!devices.containsKey(command.get(1))) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    } else if (LocalDateTime.parse(command.get(2), formatter).isBefore(initialTime)) {
                        System.out.println("ERROR: Switch time cannot be in the past!");
                        break;
                    }
                    else if (LocalDateTime.parse(command.get(2), formatter).isEqual(initialTime)) {
                        SmartDevices.switchTimes.add(LocalDateTime.parse(command.get(2), formatter));
                        devices.get(command.get(1)).setSwitchTime(LocalDateTime.parse(command.get(2), formatter));
                    }
                    else {
                        SmartDevices.switchTimes.add(LocalDateTime.parse(command.get(2), formatter));
                        devices.get(command.get(1)).setSwitchTime(LocalDateTime.parse(command.get(2), formatter));
                    }
                    break;
                case "Switch":
                    if(command.size()!=3){
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    }
                    if (! devices.containsKey(command.get(1))) {
                        System.out.println("ERROR: There is not such a device!");
                        break;
                    } else if (devices.containsKey(command.get(1))&&Commands.isInitialStatus(command.get(2))) {
                        devices.get(command.get(1)).switchDevice(command.get(2));
                        break;
                    }
                    break;
                case "ChangeName":
                    if (command.size() != 3) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    } else {
                        SmartDevices.changeName(command.get(1), command.get(2));
                        break;
                    }
                case "PlugIn":
                    if (command.size() != 3) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    } else if (devices.containsKey(command.get(1))&&Commands.isAmpere(command.get(2))) {
                        if (!(SmartHome.devices.get(command.get(1)) instanceof SmartPlug)) {
                            System.out.println("ERROR: This device is not a smart plug!");
                            break;
                        }
                        SmartHome.devices.get(command.get(1)).plugIn(command.get(1), Double.parseDouble(command.get(2)));
                        break;
                    }
                    break;
                case "PlugOut":
                    if (command.size() != 2) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    } else if(devices.containsKey(command.get(1))) {
                        if (!(SmartHome.devices.get(command.get(1)) instanceof SmartPlug)) {
                            System.out.println("ERROR: This device is not a smart plug!");
                            break;
                        }
                        SmartHome.devices.get(command.get(1)).plugOut(command.get(1));
                        break;
                    }
                case "SetKelvin":
                    if (command.size() != 3) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    }
                    if (!(devices.get(command.get(1)) instanceof SmartLamp || devices.get(command.get(1)) instanceof SmartColorLamp)) {
                        System.out.println("ERROR: This device is not a smart lamp!");
                        break;
                    }
                    if (!devices.containsKey(command.get(1))) {
                        System.out.println("ERROR: There is not such a device!");
                        break;
                    }
                    else if (Commands.isKelvin(command.get(2))) {
                        ((SmartLamp) devices.get(command.get(1))).setKelvin(Integer.parseInt(command.get(2)));
                        if (devices.get(command.get(1)) instanceof SmartColorLamp) {
                            ((SmartColorLamp) devices.get(command.get(1))).setColorMode(false);
                        }
                        break;
                    }
                    break;
                case "SetBrightness":
                    if (command.size() != 3) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    }
                    if (!devices.containsKey(command.get(1))) {
                        System.out.println("ERROR: There is not such a device!");
                        break;
                    }
                    if (!(devices.get(command.get(1)) instanceof SmartLamp || devices.get(command.get(1)) instanceof SmartColorLamp)) {
                        System.out.println("ERROR: This device is not a smart lamp!");
                        break;
                    } else if (Commands.isBrightness(command.get(2))) {
                        ((SmartLamp) devices.get(command.get(1))).setBrightness(Integer.parseInt(command.get(2)));
                        break;
                    }
                    break;
                case "SetColorCode":
                    if (command.size() != 3) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    }
                    if (!devices.containsKey(command.get(1))) {
                        System.out.println("ERROR: There is not such a device!");
                        break;
                    }
                    if (!(devices.get(command.get(1)) instanceof SmartColorLamp)) {
                        System.out.println("ERROR: This device is not a smart color lamp!");
                        break;
                    } else if (Commands.isColored(command.get(2))) {
                        ((SmartColorLamp) devices.get(command.get(1))).setColorCode(command.get(2));
                        if (devices.get(command.get(1)) instanceof SmartColorLamp) {
                            ((SmartColorLamp) devices.get(command.get(1))).setColorMode(true);
                        }
                        break;
                    }
                case "SetWhite":
                    if (command.size() != 4) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    }
                    if (!devices.containsKey(command.get(1))) {
                        System.out.println("ERROR: There is not such a device!");
                        break;
                    }
                    if (!(devices.get(command.get(1)) instanceof SmartLamp || devices.get(command.get(1)) instanceof SmartColorLamp)) {
                        System.out.println("ERROR: This device is not a smart lamp!");
                        break;
                    } else if (Commands.isKelvin(command.get(2)) && Commands.isBrightness(command.get(3))) {
                        ((SmartLamp) devices.get(command.get(1))).setWhite(command.get(2), command.get(3));
                        if (devices.get(command.get(1)) instanceof SmartColorLamp) {
                            ((SmartColorLamp) devices.get(command.get(1))).setColorMode(false);
                        }
                        break;
                    }
                    break;
                case "SetColor":
                    if (command.size() != 4) {
                        System.out.println("ERROR: Erroneous command!");
                        break;
                    }
                    if (!devices.containsKey(command.get(1))) {
                        System.out.println("ERROR: There is not such a device!");
                        break;
                    }
                    if (!(devices.get(command.get(1)) instanceof SmartColorLamp)) {
                        System.out.println("ERROR: This device is not a smart color lamp!");
                        break;
                    } else if (Commands.isColored(command.get(2)) && Commands.isBrightness(command.get(3))) {
                        ((SmartColorLamp) devices.get(command.get(1))).setColor(command.get(2), Integer.parseInt(command.get(3)));
                        if (devices.get(command.get(1)) instanceof SmartColorLamp) {
                            ((SmartColorLamp) devices.get(command.get(1))).setColorMode(true);
                        }
                        break;
                    }
                    break;
                default:
                    System.out.println("ERROR: Erroneous command!");
                    break;
            }
        }
        if (!inputs.get(inputs.size() - 1).equals(Collections.singletonList("ZReport"))) {
            System.out.println("ZReport:");
            System.out.println("Time is:\t" + initialTime.format(formatter));
            Commands.zReport();
        }
    }
}
