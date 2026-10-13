public class Main {

    public static void main(String[] args) {

        // Member details
        String name = "Simran";
        int age = 21;
        String gender = "Female";
        String phone = "9876543210";
        String email = "simran@gmail.com";
        double weight = 65;
        double height = 170;
        double bmi = weight / ((height / 100) * (height / 100));
        String fitnessLevel = "Beginner";
        String goal = "Muscle Gain";
        int days = 4;

        // Create Member object
        Member member = new Member(
        name,
        age,
        gender,
        phone,
        email,
        weight,
        height,
        bmi,
        fitnessLevel,
        goal
);

        // Save member to database
        MemberDAO dao = new MemberDAO();
        dao.addMember(member);

        // AI Workout Recommendation
        WorkoutRecommendation recommendation =
                new WorkoutRecommendation();

        String workout = recommendation.getWorkout(
                goal,
                fitnessLevel,
                days
        );

        System.out.println();
        System.out.println("===== FitZone AI Recommendation =====");
        System.out.println("Member: " + name);
        System.out.println("Goal: " + goal);
        System.out.println("Fitness Level: " + fitnessLevel);
        System.out.println("Days Available: " + days);
        System.out.println("Recommended Workout: " + workout);
    WorkoutPlan plan = new WorkoutPlan();

plan.showPlan(goal, days);
    }
}