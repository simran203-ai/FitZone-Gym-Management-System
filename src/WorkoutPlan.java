public class WorkoutPlan {

    public void showPlan(String goal, int days) {

        System.out.println();
        System.out.println("===== WEEKLY WORKOUT PLAN =====");

        if (goal.equalsIgnoreCase("Muscle Gain")) {

            if (days == 3) {
                System.out.println("Monday    : Chest + Triceps");
                System.out.println("Wednesday : Back + Biceps");
                System.out.println("Friday    : Legs + Shoulders");
            }

            else if (days == 4) {
                System.out.println("Monday    : Chest + Triceps");
                System.out.println("Tuesday   : Back + Biceps");
                System.out.println("Thursday  : Legs");
                System.out.println("Friday    : Shoulders + Abs");
            }

            else {
                System.out.println("Monday    : Chest + Triceps");
                System.out.println("Tuesday   : Back + Biceps");
                System.out.println("Wednesday : Legs");
                System.out.println("Thursday  : Shoulders + Abs");
                System.out.println("Friday    : Full Body");
            }

        } else if (goal.equalsIgnoreCase("Weight Loss")) {

            System.out.println("Monday    : Cardio + Full Body");
            System.out.println("Tuesday   : HIIT");
            System.out.println("Wednesday : Cardio");
            System.out.println("Thursday  : Full Body");
            System.out.println("Friday    : Cardio + Abs");

        } else {

            System.out.println("Monday    : Full Body");
            System.out.println("Tuesday   : Cardio");
            System.out.println("Wednesday : Full Body");
            System.out.println("Thursday  : Cardio");
            System.out.println("Friday    : Full Body");
        }
    }
}