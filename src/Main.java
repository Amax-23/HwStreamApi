import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        System.out.println("Количество людей в списке: " + persons.size() + " человек.");

        long numberMinors = persons.stream().filter(person -> person.getAge() < 18).count();
        System.out.println("Количество несовершеннолетних в списке: " + numberMinors + " человек.");

        List<String> surnamesСonscripts = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27
                        && person.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        //System.out.println("Список фамилий людей призывного возраста: " + surnamesСonscripts);
        System.out.println("Количество мужчин призывного возраста от 18 до 27 лет: "
                + surnamesСonscripts.size()  + " человек.");

        List<String> surnamesOperable = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 65 && person.getEducation() == Education.HIGHER)
                .filter(Main::sort)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::getFamily)
                .collect(Collectors.toList());
        //System.out.println("Список фамилий людей трудоспособного возраста: " + surnamesOperable);
        System.out.println("Количество людей трудоспособного возраста с высшим образованием: "
                + surnamesOperable.size()  + " человек.");
    }

    public static boolean sort(Person person) {
        if (person.getSex() == Sex.WOMAN && person.getAge() <= 60) {
            return true;
        } else if (person.getSex() != Sex.WOMAN) {
            return true;
        } else return false;
    }

    public enum Sex {
        MAN,
        WOMAN
    }

    public enum Education {
        ELEMENTARY,
        SECONDARY,
        FURTHER,
        HIGHER
    }

    static class Person {
        private String name;
        private String family;
        private Integer age;
        private Sex sex;
        private Education education;

        public Person(String name, String family, int age, Sex sex, Education education) {
            this.name = name;
            this.family = family;
            this.age = age;
            this.sex = sex;
            this.education = education;
        }

        public String getName() {
            return name;
        }

        public String getFamily() {
            return family;
        }

        public Integer getAge() {
            return age;
        }

        public Sex getSex() {
            return sex;
        }

        public Education getEducation() {
            return education;
        }

        @Override
        public String toString() {
            return "Person {" +
                    "name='" + name + '\'' +
                    ", family='" + family + '\'' +
                    ", age = " + age +
                    ", sex = " + sex +
                    ", education = " + education +
                    '}';
        }

    }
}
