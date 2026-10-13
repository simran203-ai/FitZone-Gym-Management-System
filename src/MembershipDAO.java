import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MembershipDAO {

    public static boolean addMembership(
            int memberId,
            String plan,
            String startDate,
            String endDate,
            double amount,
            String paymentStatus) {

        String sql = "INSERT INTO memberships " +
                "(member_id, plan_name, duration, price, start_date, end_date, payment_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ps.setString(2, plan);

            int duration = 1;

            if (plan.equalsIgnoreCase("Standard")) {
                duration = 3;
            } else if (plan.equalsIgnoreCase("Premium")) {
                duration = 6;
            }

            ps.setInt(3, duration);
            ps.setDouble(4, amount);
            ps.setString(5, startDate);
            ps.setString(6, endDate);
            ps.setString(7, paymentStatus);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public static int getMembershipCount() {

        String sql = "SELECT COUNT(*) FROM memberships";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
    
public static class MembershipInfo {
    public final String plan;
    public final String startDate;
    public final String endDate;
    public final String paymentStatus;

    public MembershipInfo(
            String plan,
            String startDate,
            String endDate,
            String paymentStatus) {

        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentStatus = paymentStatus;
    }
}

public static MembershipInfo getLatestMembership(int memberId)
        throws java.sql.SQLException {

    String sql = "SELECT plan_name, start_date, end_date, " +
            "payment_status FROM memberships " +
            "WHERE member_id = ? " +
            "ORDER BY end_date DESC LIMIT 1";

    try (Connection con = DatabaseConnection.getConnection()) {

        if (con == null) {
            throw new java.sql.SQLException(
                    "Cannot connect to the FitZone database.");
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new MembershipInfo(
                            rs.getString("plan_name"),
                            rs.getString("start_date"),
                            rs.getString("end_date"),
                            rs.getString("payment_status")
                    );
                }

                return null;
            }
        }
    }
}

}