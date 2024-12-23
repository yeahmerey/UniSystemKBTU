package Controllers;

import java.util.Timer;
import java.util.TimerTask;

import DLL.DBContext;
import Users.Manager;
import Users.Teacher;
import Utils.FinanceOffice;

public class FinanceManagerController {
	public static void giveSalary() {
		int sum = DBContext.getManagers().size() * 200000 + DBContext.getTeachers().size() * 300000  ;
		if(FinanceOffice.budjet > 0 && FinanceOffice.budjet > sum) {
			FinanceOffice.budjet -= sum;
			System.out.println("Manager gived salary.");
			for (Manager manager : DBContext.getManagers()) {
				manager.setSalaryPaid();
            }
			for(Teacher teacher : DBContext.getTeachers()) {
				teacher.setSalaryPaid();
			}
			scheduleResetSalaryStatus();
			
		}else {
			System.out.println("Not enough money in the budget");
		}
	}
	
	public static double seeBudjet() {
		return FinanceOffice.getBudjet();
	}
	private static void scheduleResetSalaryStatus() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Manager manager : DBContext.getManagers()) {
                    manager.resetSalaryPaid(); 
                }
                for (Teacher teacher : DBContext.getTeachers()) {
                    teacher.resetSalaryPaid(); 
                }
                System.out.println("All salaries reset to unpaid status.");
            }
        }, 60 * 1000);
    }

}
