public class Member {

    private int id;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String email;
    private double weight;
    private double height;
    private double bmi;
    private String fitnessLevel;
    private String goal;

    // Constructor used when registering a new member
    public Member(String name, int age, String gender, String phone,
                  String email, double weight, double height, double bmi,
                  String fitnessLevel, String goal) {

        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.fitnessLevel = fitnessLevel;
        this.goal = goal;
    }

    // Constructor used when reading a member from MySQL
    public Member(int id, String name, int age, String gender, String phone,
                  String email, double weight, double height, double bmi,
                  String fitnessLevel, String goal) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.fitnessLevel = fitnessLevel;
        this.goal = goal;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double getBmi() {
        return bmi;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public String getGoal() {
        return goal;
    }
}
