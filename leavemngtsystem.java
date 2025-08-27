package leavemanagementsystem;
import java.util.*;

class Employee {
    int id;
    String name;
    int leaveBalance;

    public Employee(int id, String name, int leaveBalance) {
        this.id = id;
        this.name = name;
        this.leaveBalance = leaveBalance;
    }
}

class LeaveRequest {
    int empId;
    String leaveType;
    int days;
    String status;

    public LeaveRequest(int empId, String leaveType, int days) {
        this.empId = empId;
        this.leaveType = leaveType;
        this.days = days;
        this.status = "Pending";
    }
}

public class leavemngtsystem {
	static Map<Integer, Employee> employees = new HashMap<>();
    static List<LeaveRequest> leaveRequests = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Adding some employees
        employees.put(1, new Employee(1, "Akhila", 10));
        employees.put(2, new Employee(2, "Ravi", 8));
        employees.put(3, new Employee(3, "Priya", 12));

        while (true) {
            System.out.println("\n==== Employee Leave Management System ====");
            System.out.println("1. Apply Leave");
            System.out.println("2. Approve/Reject Leave");
            System.out.println("3. View Leave Requests");
            System.out.println("4. View Leave Balance");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    applyLeave(sc);
                    break;
                case 2:
                    approveLeave(sc);
                    break;
                case 3:
                    viewRequests();
                    break;
                case 4:
                    viewBalance(sc);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void applyLeave(Scanner sc) {
        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();
        sc.nextLine();
        if (!employees.containsKey(empId)) {
            System.out.println("Employee not found!");
            return;
        }
        System.out.print("Enter Leave Type (Casual/Sick): ");
        String leaveType = sc.nextLine();
        System.out.print("Enter number of days: ");
        int days = sc.nextInt();

        LeaveRequest request = new LeaveRequest(empId, leaveType, days);
        leaveRequests.add(request);
        System.out.println("Leave request submitted successfully.");
    }

    public static void approveLeave(Scanner sc) {
        if (leaveRequests.isEmpty()) {
            System.out.println("No leave requests found!");
            return;
        }
        for (int i = 0; i < leaveRequests.size(); i++) {
            LeaveRequest lr = leaveRequests.get(i);
            System.out.println(i + ". EmpID: " + lr.empId + ", Type: " + lr.leaveType +
                               ", Days: " + lr.days + ", Status: " + lr.status);
        }
        System.out.print("Enter request number to approve/reject: ");
        int reqNo = sc.nextInt();
        if (reqNo < 0 || reqNo >= leaveRequests.size()) {
            System.out.println("Invalid request number!");
            return;
        }
        LeaveRequest req = leaveRequests.get(reqNo);
        System.out.print("Approve or Reject (A/R): ");
        char decision = sc.next().charAt(0);
        if (decision == 'A' || decision == 'a') {
            Employee emp = employees.get(req.empId);
            if (emp.leaveBalance >= req.days) {
                emp.leaveBalance -= req.days;
                req.status = "Approved";
                System.out.println("Leave Approved.");
            } else {
                req.status = "Rejected (Insufficient balance)";
                System.out.println("Leave Rejected due to insufficient balance.");
            }
        } else {
            req.status = "Rejected";
            System.out.println("Leave Rejected.");
        }
    }

    public static void viewRequests() {
        if (leaveRequests.isEmpty()) {
            System.out.println("No leave requests available.");
            return;
        }
        for (LeaveRequest lr : leaveRequests) {
            System.out.println("EmpID: " + lr.empId + ", Type: " + lr.leaveType +
                               ", Days: " + lr.days + ", Status: " + lr.status);
        }
    }

    public static void viewBalance(Scanner sc) {
        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();
        if (!employees.containsKey(empId)) {
            System.out.println("Employee not found!");
            return;
        }
        Employee emp = employees.get(empId);
        System.out.println("Employee: " + emp.name + ", Leave Balance: " + emp.leaveBalance);
    }
}


