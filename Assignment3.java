import java.util.ArrayList;
import java.util.Scanner;

abstract class WeatherEvent {
    private String location;
    private static int nextId = 0;
    private int id;
    private boolean active;

    public WeatherEvent(String location, boolean active) {
        this.location = location;
        this.active = active;
        id = nextId;
        nextId++;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Weather Event Location :" + location + "\nID: " + id + "\nActive : " + active;
    }
}
abstract class Precipitation extends WeatherEvent{
    private double rateOfFall;

    public double getRateOfFall() {
        return rateOfFall;
    }

    public void setRateOfFall(double rateOfFall) {
        if (rateOfFall >= 0) {
            this.rateOfFall = rateOfFall;
        } else {return;}
    }
    public Precipitation(String location, boolean active, double rateOfFall){
        super(location,active);
        this.rateOfFall = rateOfFall;
    }

    public String toString(){
        String result;
        if (rateOfFall < 0.5){
            result = "Light";
        } else if (rateOfFall >= 0.5 && rateOfFall <= 1.5) {
            result = "Medium";
        } else {
            result = "Heavy";
        }
        return super.toString() + "\nRate of Fall: " + rateOfFall + " in/h "+result;
    }
}
abstract class Obscuration extends WeatherEvent {
    private int visibility;

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        if (visibility>=1){
            this.visibility = visibility;
        } else this.visibility = 1;
    }
    public Obscuration(String location, boolean active, int visibility){
        super(location, active);
        this.visibility = visibility;
    }
    public String toString(){
        String visib;
        if (visibility > 56){
            visib = "Normal";
        } else visib = visibility + "/8 mi";
        return super.toString() + "\nVisibility: " + visib;
    }
}
class Rain extends Precipitation{
    private double dropSize;

    public double getDropSize() {
        return dropSize;
    }

    public void setDropSize(double dropSize) {
        if (dropSize >= 0.02) {
            this.dropSize = dropSize;
        } else return;
    }
    public Rain (String location, boolean active, double rateOfFall){
        super(location,active,rateOfFall);
    }

    public String toString (){
        String drip;
        if (dropSize < 0.066){
            drip = "Small";
        } else if (dropSize >0.066 && dropSize < 0.112) {
            drip = "Medium";
        } else drip = "Large";
        return super.toString() + "\nDroplet size: " + dropSize + " " + drip;
    }
}

class Snow extends Precipitation {
    private double temperature;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        if (temperature > -459.67 && temperature < 32) {
            this.temperature = temperature;
        } else if (temperature <= -459.67) {
            this.temperature = -459.67;
        } else if (temperature >= 32) {
            this.temperature = 32;
        }else return;
    }

    public Snow(String location, boolean active, double rateOfFall) {
        super(location, active, rateOfFall);
    }

    public String toString() {
        String rateOfFallReturn;
        if (getRateOfFall() < 0.5) {
            rateOfFallReturn = "Light";
        } else if (getRateOfFall() > 0.5 && getRateOfFall() < 1.5) {
            rateOfFallReturn = "Medium";
        } else rateOfFallReturn = "Heavy";
        return super.toString() + "\nTemperature: " + temperature + " F";
    }
}
    class Fog extends Obscuration {
        private boolean freezingFog;

        public boolean getFreezingFog() {
            return freezingFog;
        }

        public void setFreezingFog(boolean freezingFog) {
            this.freezingFog = freezingFog;
        }

        public Fog(String location, boolean active, int visibility) {
            super(location, active, visibility);
        }

        @Override
        public void setVisibility(int visibility) {
            if (visibility >= 5) {
                super.setVisibility(4);
            } else if (visibility <= 0) {
                super.setVisibility(1);
            } else super.setVisibility(visibility);
        }

        public String toString() {
            String alert;
            if (freezingFog) {
                alert = "ALERT! FREEZING FOG!";
            } else alert = null;
            return super.toString() + "\n" + alert;
        }
    }

class Particle extends Obscuration{
    String particleType;
    public String getParticleType() {
        return particleType;
    }
    public void setParticleType(String particleType) {
        if (particleType.equals("Dust") || particleType.equals("Sand") || particleType.equals("Ash")) {
            this.particleType = particleType;
        } else this.particleType = "Other";
    }
    public Particle(String location, boolean active, int visibility) {
        super(location, active, visibility);
    }
    public String toString(){
        return super.toString() + "\nParticle type: " + particleType;
    }
}
public class Assignment3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<WeatherEvent> events = new ArrayList<>();
        boolean quit = false;
        do {
            System.out.println("1. Add weather event \n" +
                    "2. Update location \n" +
                    "3. Update active \n" +
                    "4. View all events \n" +
                    "5. Quit \n" +
                    "Enter your option: ");
            int choice = Integer.parseInt(sc.next());
            switch (choice) {
                case 1:
                    boolean activeOne = true;
                    System.out.println("1. Rain \n" +
                            "2. Snow \n" +
                            "3. Fog \n" +
                            "4. Particle \n" +
                            "What type of weather event is being added? ");
                    int typeOne = Integer.parseInt(sc.next());
                    System.out.println("Where is the event happening? ");
                    String locationOne = sc.next();
                    if (typeOne == 1) {
                        System.out.println("What is the rate of fall? (in/h) ");
                        double rateOfFallOne = Double.parseDouble(sc.next());
                        System.out.println("What is the diameter of the drops? (in) ");
                        double dropletsOne = Double.parseDouble(sc.next());
                        Rain rain = new Rain(locationOne, activeOne, rateOfFallOne);
                        rain.setDropSize(dropletsOne);
                        events.add(rain);
                    } else if (typeOne == 2) {
                        System.out.println("What is the rate of fall? (in/h) ");
                        double rateOfFallTwo = Double.parseDouble(sc.next());
                        System.out.println("What is the temperature? (F) ");
                        double temperatureTwo = Double.parseDouble(sc.next());
                        Snow snow = new Snow(locationOne, activeOne, rateOfFallTwo);
                        snow.setTemperature(temperatureTwo);
                        events.add(snow);
                    } else if (typeOne == 3) {
                        System.out.println("What is the visibility? (1/8 mi) ");
                        int visibilityOne = Integer.parseInt(sc.next());
                        System.out.println("Is the fog freezing? (y/n) ");
                        String responseToFog = sc.next();
                        boolean freezing;
                        if (responseToFog.equals("y".toLowerCase())) {
                            freezing = true;
                        } else freezing = false;
                        Fog fog = new Fog(locationOne, activeOne, visibilityOne);
                        fog.setFreezingFog(freezing);
                        events.add(fog);
                    } else if (typeOne == 4) {
                        System.out.println("What is the visibility? (1/8 mi) ");
                        int visibilityOne = Integer.parseInt(sc.next());
                        System.out.println("What is the obscuration made of? (Sand/Dust/Ash/Other) ");
                        String responseToObs = sc.next();
                        Particle particle = new Particle(locationOne, activeOne, visibilityOne);
                        particle.setParticleType(responseToObs);
                        events.add(particle);
                    } else System.out.println("Invalid Choice!");
                    break;
                case 2:
                    System.out.println("Enter Id of weather location: ");
                    int caseTwo = Integer.parseInt(sc.next());
                    System.out.println("Enter the new location of the weather event (currently “" + events.get(caseTwo).getLocation() +"”): ");
                    String newLocation = sc.next();
                    events.get(caseTwo).setLocation(newLocation);
                    break;
                case 3:
                    System.out.println("Enter Id of weather event: ");
                    int caseThree = Integer.parseInt(sc.next());
                    events.get(caseThree).setActive(false);
                    System.out.println("Event set to inactive.");
                    break;
                case 4:
                    //Geek for Geek website
                    events.forEach((n) -> System.out.println(n + "\n"));
                    break;
                case 5:
                    System.out.println("Shutting off...");
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (quit == false);
    }
}
