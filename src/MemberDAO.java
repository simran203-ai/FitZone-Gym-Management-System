import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // Add new member
    public void addMember(Member member) {

        String sql = "INSERT INTO members " +
                "(name, age, gender, phone, email, weight, height, bmi, fitness_level, goal) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, member.getName());
            ps.setInt(2, member.getAge());
            ps.setString(3, member.getGender());
            ps.setString(4, member.getPhone());
            ps.setString(5, member.getEmail());
            ps.setDouble(6, member.getWeight());
            ps.setDouble(7, member.getHeight());
            ps.setDouble(8, member.getBmi());
            ps.setString(9, member.getFitnessLevel());
            ps.setString(10, member.getGoal());

            ps.executeUpdate();

            System.out.println("Member added successfully!");

            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("Error while adding member!");
            e.printStackTrace();
        }
    }


    // Get all members from MySQL
    public List<Member> getAllMembers() {

        List<Member> members = new ArrayList<>();

        String sql = "SELECT * FROM members";

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Member member = new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDouble("weight"),
                        rs.getDouble("height"),
                        rs.getDouble("bmi"),
                        rs.getString("fitness_level"),
                        rs.getString("goal")
                );

                members.add(member);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("Error while fetching members!");
            e.printStackTrace();
        }

        return members;
    }
        // Get total number of members
    public static int getMemberCount() {

        String sql = "SELECT COUNT(*) FROM members";

        try {

            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                rs.close();
                ps.close();
                con.close();

                return count;
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("Error while counting members!");
            e.printStackTrace();
        }

        return 0;
    }
    public boolean memberExists(String phone, String email) {

    String sql = "SELECT id FROM members WHERE phone = ? OR email = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, phone);
        ps.setString(2, email);

        ResultSet rs = ps.executeQuery();

        return rs.next();

    } catch (Exception e) {

        e.printStackTrace();
        return false;
    }
}

public static boolean deleteMember(int memberId) {
    String sql = "DELETE FROM members WHERE id = ?";

    try (java.sql.Connection con =
             DatabaseConnection.getConnection();
         java.sql.PreparedStatement ps =
             con.prepareStatement(sql)) {

        ps.setInt(1, memberId);

        return ps.executeUpdate() > 0;

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public static boolean updateMember(
        int memberId,
        String name,
        int age,
        String phone,
        double weight,
        double height,
        String fitnessLevel,
        String goal) {

    String sql = "UPDATE members SET name=?, age=?, phone=?, " +
                 "weight=?, height=?, fitness_level=?, goal=? " +
                 "WHERE id=?";

    try (java.sql.Connection con =
             DatabaseConnection.getConnection();
         java.sql.PreparedStatement ps =
             con.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, phone);
        ps.setDouble(4, weight);
        ps.setDouble(5, height);
        ps.setString(6, fitnessLevel);
        ps.setString(7, goal);
        ps.setInt(8, memberId);

        return ps.executeUpdate() > 0;

    } catch (java.sql.SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}

public Member loginMember(String name, String phone)
        throws java.sql.SQLException {

    String sql = "SELECT id, name, age, gender, phone, email, " +
            "weight, height, bmi, fitness_level, goal " +
            "FROM members " +
            "WHERE LOWER(TRIM(name)) = LOWER(?) " +
            "AND TRIM(phone) = ? LIMIT 1";

    try (java.sql.Connection con =
                 DatabaseConnection.getConnection()) {

        if (con == null) {
            throw new java.sql.SQLException(
                    "Cannot connect to the FitZone database.");
        }

        try (java.sql.PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ps.setString(2, phone.trim());

            try (java.sql.ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Member(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getDouble("weight"),
                            rs.getDouble("height"),
                            rs.getDouble("bmi"),
                            rs.getString("fitness_level"),
                            rs.getString("goal")
                    );
                }

                return null;
            }
        }
    }
}

}
