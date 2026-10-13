public class WorkoutRecommendation {

    public String getWorkout(String goal, String fitnessLevel, int days) {

        if (goal.equalsIgnoreCase("Weight Loss")) {

            if (days <= 3) {
                return "Full Body + Cardio";
            } else {
                return "Cardio + Full Body + HIIT";
            }

        } else if (goal.equalsIgnoreCase("Muscle Gain")) {

            if (fitnessLevel.equalsIgnoreCase("Beginner")) {
                return "Strength Training - 3 Days";
            } else {
                return "Strength Training - 4 to 5 Days";
            }

        } else if (goal.equalsIgnoreCase("Fitness")) {

            return "Full Body + Cardio";

        } else {

            return "Basic Full Body Workout";
        }
    }
}
