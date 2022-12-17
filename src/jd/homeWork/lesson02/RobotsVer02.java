package jd.homeWork.lesson02;

import java.util.*;

/*
Задача
Двое безумных учёных устроили соревнование, кто из них соберёт больше роботов за 100 ночей.
Для этого каждую ночь каждый из них отправляет своего прислужника на свалку фабрики
роботов за деталями.
Чтобы собрать одного робота им нужно:
Голова, Тело, Левая рука, Правая рука, Левая нога, Правая нога, CPU, RAM, HDD
В первую ночь на свалке находится 20 случайных деталей. Каждую ночь фабрика выбрасывает
на свалку от 1 до 4 случайных деталей.
В то же самое время прислужники обоих учёных отправляются на свалку, и каждый собирает
от 1 до 4 случайных деталей. Если на свалке деталей нет – прислужник уходит ни с чем.
Затем они возвращаются домой и отдают детали хозяевам.
Учёные пытаются собрать как можно больше роботов из деталей, которые они получили.
Написать программу, симулирующую этот процесс. Для симуляции принять, что каждая ночь
наступает через 100мс.

Фабрика и два прислужника действуют в одно и то же время.
После 100 ночей (около 10 секунд) определить победителя соревнования.

 */
public class RobotsVer02 {

    private static final Random random = new Random();

    private static void robotFactoryInitial(Map<String, Integer> map) {
        map.put("head", 0);
        map.put("body", 0);
        map.put("handLeft", 0);
        map.put("handRight", 0);
        map.put("legLeft", 0);
        map.put("legRight", 0);
        map.put("cpu", 0);
        map.put("ram", 0);
        map.put("hdd", 0);
    }


    public static void main(String[] args) {

        List<String> firstNightRandomParts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            firstNightRandomParts.add(randomPart());
        }

//        System.out.println(firstNightRandomParts);

        Iterator<String> iterator = firstNightRandomParts.iterator();
        while (iterator.hasNext()) {
            String nextString = iterator.next();
            int j = random.nextInt(2);
            if (j == 0) {
                Scientist01.takeParts(nextString);
            } else {
                Scientist02.takeParts(nextString);
            }
            iterator.remove();
        }
//        System.out.println(Scientist01.newPartsList);
//        System.out.println(Scientist02.newPartsList);
//        System.out.println(firstNightRandomParts);

        robotFactoryInitial(Scientist01.robotFactoryMap);
        robotFactoryInitial(Scientist02.robotFactoryMap);


//        System.out.println(Scientist01.robotFactoryMap);


        for (int i = 0; i < 100; i++) {
            List<String> list = randomNightCountParts();
            for (Iterator<String> iter = list.listIterator(); iter.hasNext(); ) {
                String a = iter.next();
                int j = random.nextInt(2);
                if (j == 0) {
                    Scientist01.takeParts(a);
                } else {
                    Scientist02.takeParts(a);
                }
                iter.remove();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        Scientist01.setRobotFactory(Scientist01.newPartsList);
        Scientist02.setRobotFactory(Scientist02.newPartsList);


//        System.out.println(firstNightRandomParts);
//        System.out.println(randomNightCountParts());

//        System.out.println(Scientist01.robotFactoryMap);
//        System.out.println(Scientist02.robotFactoryMap);
//
//        System.out.println(Scientist01.countOfRobots());
//        System.out.println(Scientist02.countOfRobots());

        winner(Scientist01.countOfRobots(), Scientist02.countOfRobots());

    }

    public static void winner(int sc01, int sc02) {
        if (sc01 > sc02) {
            System.out.println("Победил первый ученый. Он собрал " + sc01 + " роботов. Ура!!!");
        } else if (sc02 > sc01) {
            System.out.println("Победил второй ученый. Он собрал " + sc02 + " роботов. Ура!!!");
        } else {
            System.out.println("Ничья. Ученые собрали каждый по " + sc01 + " роботов");
        }
    }

    private static List<String> randomNightCountParts() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < random.nextInt(4) + 1; i++) {
            list.add(randomPart());
        }
        return list;
    }

    private static String randomPart() {
        int randomNumber = random.nextInt(9);

        return switch (randomNumber) {
            case 0 -> "head";
            case 1 -> "body";
            case 2 -> "handLeft";
            case 3 -> "handRight";
            case 4 -> "legLeft";
            case 5 -> "legRight";
            case 6 -> "cpu";
            case 7 -> "ram";
            case 8 -> "hdd";
            default -> throw new IllegalStateException("Unexpected value: " + randomNumber);
        };
    }

    public static class Scientist01 {
        static List<String> newPartsList = new ArrayList<>();
        static Map<String, Integer> robotFactoryMap = new HashMap<>();

        public static void takeParts(String s) {
            newPartsList.add(s);
        }

        public static void setRobotFactory(List<String> stringList) {
            for (String str : stringList) {
                robotFactoryMap.computeIfPresent(str, (k, v) -> v + 1);
            }
        }

        public static int countOfRobots() {
            return Collections.min(robotFactoryMap.values());
        }
    }

    public static class Scientist02 {
        static List<String> newPartsList = new ArrayList<>();
        static Map<String, Integer> robotFactoryMap = new HashMap<>();

        public static void takeParts(String s) {
            newPartsList.add(s);
        }

        public static void setRobotFactory(List<String> stringList) {
            for (String str : stringList) {
                robotFactoryMap.computeIfPresent(str, (k, v) -> v + 1);
            }
        }

        public static int countOfRobots() {
            return Collections.min(robotFactoryMap.values());
        }
    }

}


