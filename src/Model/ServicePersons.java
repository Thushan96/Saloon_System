package Model;

import java.util.Objects;

public class ServicePersons {
    private String EmpId;
    private String EmpName;

    public ServicePersons(String empId, String empName) {
        setEmpId(empId);
        setEmpName(empName);
    }

    public ServicePersons() {
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServicePersons)) return false;
        ServicePersons that = (ServicePersons) o;
        return Objects.equals(getEmpId(), that.getEmpId()) &&
                Objects.equals(getEmpName(), that.getEmpName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmpId(), getEmpName());
    }

    @Override
    public String toString() {
        return "ServicePersons{" +
                "EmpId='" + EmpId + '\'' +
                ", EmpName='" + EmpName + '\'' +
                '}';
    }
}
