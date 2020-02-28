public class DynamicVar {
    public int currentDoctorId;
    public int currentNurseId;
    public int currentClerkId;
    private DynamicVar() {
        currentDoctorId = 0;
        currentNurseId = 0;
        currentClerkId = 0;
    }
    private static DynamicVar v = new DynamicVar();
    public static DynamicVar getDynamo() { return v; }
}