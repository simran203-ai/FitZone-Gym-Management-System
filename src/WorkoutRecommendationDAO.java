
import java.sql.Connection;
import java.sql.PreparedStatement;

public class WorkoutRecommendationDAO {

    public static boolean saveRecommendation(
            int memberId,
            int age,
            String gender,
            double weight,
            double height,
            String fitnessLevel,
            String goal,
            int daysAvailable,
            String recommendation) {

                
        String sql = "INSERT INTO workout_recommendations "
                + "(member_id, age, gender, weight, height, "
                + "fitness_level, goal, days_available, recommendation) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setDouble(4, weight);
            ps.setDouble(5, height);
            ps.setString(6, fitnessLevel);
            ps.setString(7, goal);
            ps.setInt(8, daysAvailable);
            ps.setString(9, recommendation);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error saving workout recommendation!");
            e.printStackTrace();
            return false;
        }
    }
}
