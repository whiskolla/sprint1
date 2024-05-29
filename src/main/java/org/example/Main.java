package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Converter {
    int convertStepToCM(int step){
        return step*75;
    }
    int convertStepToKKAL(int step){
        return step*50;
    }
    int convertKALToKKAL(int kal){
        return kal*1000;
    }

}
class StepTracker {
    HashMap<Integer, MonthData> monthToData = new HashMap<Integer, MonthData>();
    Scanner sc = new Scanner(System.in);
    int month, day, steps, temp, goalSteps = 10000;
    public StepTracker(){
        for(int i=0;i<12;i++){
            monthToData.put(i, new MonthData());
        }
    }

    public void putStepsByMonth(){
        System.out.println("Месяц (1-12), день (1-30), шаги");
        System.out.println("Месяц: " + (month = sc.nextInt()) + "\nДень: " + (day = sc.nextInt()) + "\nШаги: " + (steps = sc.nextInt()));
        if(((month|day|steps) <= 0) | (month > 12) | (day>30)){ System.out.println("НЕправильный ввод!"); } else {
            monthToData.get(month-1).putStepsByDay(day-1, steps);
        }
    }

    class MonthData{
        public MonthData(){
            for(int i=0;i<30;i++){
                stepsByDay.put(i,0);
            }
        }
        Map<Integer, Integer> stepsByDay = new HashMap<Integer, Integer>(); // <номер дня, количество шагов>
        Converter cv = new Converter();
        public void putStepsByDay(int day, int steps){
            stepsByDay.put(day,steps);
        }

        public void printStaticByMonth(){
            int sum = 0, max = 0, count = 0, maxcount = 0;
            for(Map.Entry<Integer, Integer> item : stepsByDay.entrySet()){
                System.out.println(item.getKey()+1 + " день: " + item.getValue() + "\n");
                sum+=item.getValue();
                if(item.getValue() > max){max = item.getValue();}
                if(item.getValue() > goalSteps){count++;} else {
                    if((count>0) & (count > maxcount)){
                        count=maxcount;
                        count = 0;
                    }
                }
            }
            System.out.println("Общее количество шагов за месяц: " + sum);
            System.out.println("Среднее количество шагов за месяц: " + sum/stepsByDay.size());
            System.out.println("Максимальное в сутки количество шагов за месяц: " + max);
            System.out.println("Пройденная дистанция за месяц: " + cv.convertStepToCM(sum));
            System.out.println("Количество сожженных калорий за месяц: " + cv.convertStepToKKAL(sum));
        }
    }

    public void printStatic(){
        System.out.println("Месяц (1-12)");
        System.out.println("Месяц: " + (month = sc.nextInt()) + "\n");
        if((month <= 0) | (month > 12)){ System.out.println("НЕправильный ввод!"); } else {

            monthToData.get(month-1).printStaticByMonth();
        }
    }

    public void newGoal(){
        System.out.println("Ввести новую цель шагов: ");
        temp = sc.nextInt();
        if(temp < 0){
            System.out.println("Введено неправильное число.");
            temp = -1;
        } else {
            System.out.println("Цель изменена.");
            goalSteps = temp;
            temp = -1;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int menu, month, temp;
        StepTracker st = new StepTracker();

        while (true) {
            printMenu();
            menu = in.nextInt();
            switch (menu) {
                case (1):
                    st.putStepsByMonth();
                    break;
                case (2):
                    st.printStatic();
                    break;
                case (3):
                    st.newGoal();
                    break;
                case (4):
                    System.exit(0);
                default:
                    System.out.println("Введено неправильное число!");
            }
        }
    }


    public static void printMenu(){
        System.out.println(
                "1. Ввести количество шагов\n" +
                "2. Напечатать статистику за определенный месяц\n" +
                "3. Изменить цель по количеству шагов\n" +
                "4. Выйти из приложения");
    }
}