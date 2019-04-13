package EnumTest;

import java.util.ArrayList;
import java.util.List;

public class EnumTest {
    enum Day {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY {
            @Override
            public float calculatePayDay() {
                return 1.5F;
            }
        },
        SUNDAY {
            @Override
            public float calculatePayDay() {
                return 2.0F;
            }
        };

        private float payFactor = calculatePayDay();

        public float calculatePayDay() {
            return 1.0F;
        }

        public float calculateDailyPay(int basicDailyPay) {
            return payFactor * basicDailyPay;
        }


    }

    interface WorkingDays {
        List<Day> getDays();
    }


    static WorkingDays workingDays = new WorkingDays() {
        @Override
        public List<Day> getDays() {
            List<Day> x = new ArrayList();
            x.add(Day.MONDAY);
            x.add(Day.TUESDAY);
            x.add(Day.WEDNESDAY);
            x.add(Day.THURSDAY);
            x.add(Day.FRIDAY);
            return x;
        }
    };

    static class EmergencyWorkingDays implements WorkingDays {
        @Override
        public List<Day> getDays() {
            List<Day> x = new ArrayList();
            x.add(Day.MONDAY);
            x.add(Day.TUESDAY);
            x.add(Day.WEDNESDAY);
            x.add(Day.THURSDAY);
            x.add(Day.FRIDAY);
            x.add(Day.SATURDAY);
            x.add(Day.SUNDAY);
            return x;
        }
    }


    public static void main(String[] args) {

        WorkingDays labourWorkingDays = new WorkingDays() {
            @Override
            public List<Day> getDays() {
                List<Day> x = new ArrayList();
                x.add(Day.MONDAY);
                x.add(Day.TUESDAY);
                x.add(Day.THURSDAY);
                x.add(Day.SATURDAY);
                return x;
            }
        };

        EmergencyWorkingDays emergencyWorkingDays = new EmergencyWorkingDays();

        Person first = new Person("John", workingDays, 1000);
        Person second = new Person("Alice", emergencyWorkingDays, 1500);
        Person third = new Person("Ann", labourWorkingDays, 2000);
        List<Person> personList = new ArrayList<Person>();
        personList.add(first);
        personList.add(second);
        personList.add(third);

        for (Person person : personList) {
            System.out.println(person);
        }

    }
}


class Person {
    String name;
    EnumTest.WorkingDays workingDays;
    int basicDaylyPay;


    public Person(String name, EnumTest.WorkingDays workingDays, int basicDailyPay) {
        this.name = name;
        this.workingDays = workingDays;
        this.basicDaylyPay = basicDailyPay;
    }

    float calculateWeeklySalary() {
        float sum = 0;
        for (EnumTest.Day day : workingDays.getDays()) {
            sum += day.calculateDailyPay(basicDaylyPay);
        }
        return sum;
    }


    @Override
    public String toString() {
        String namesWorkingDays = "";
        for (EnumTest.Day day : workingDays.getDays()) {
            namesWorkingDays += day.name() + ", ";
        }
        return String.format("%s worked in %sand earns: %.0f", name, namesWorkingDays, calculateWeeklySalary());
    }
}