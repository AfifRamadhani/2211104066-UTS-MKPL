package lib;

public class TaxFunction {
    private static final int TAX_RATE_PERCENT = 5;
    private static final int BASE_NON_TAXABLE_INCOME = 54000000;
    private static final int SPOUSE_NON_TAXABLE_INCOME = 4500000;
    private static final int CHILD_NON_TAXABLE_INCOME = 4500000;
    private static final int MAX_CHILDREN_FOR_TAX_DEDUCTION = 3;
    
    /**
     * Menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
     * 
     * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya 
     * dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
     * 
     * - Penghasilan tidak kena pajak: Rp 54.000.000
     * - Tambahan untuk menikah: Rp 4.500.000
     * - Tambahan per anak (maks 3): Rp 4.500.000
     */
    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, 
                                  int deductible, boolean isMarried, int numberOfChildren) {
        
        validateInput(numberOfMonthWorking);
        
        int nonTaxableIncome = calculateNonTaxableIncome(isMarried, numberOfChildren);
        int yearlyIncome = calculateYearlyIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking);
        int taxableIncome = yearlyIncome - deductible - nonTaxableIncome;
        
        int tax = (int) Math.round(TAX_RATE_PERCENT / 100.0 * taxableIncome);
        
        return tax < 0 ? 0 : tax;
    }
    
    private static void validateInput(int numberOfMonthWorking) {
        if (numberOfMonthWorking > 12) {
            System.err.println("More than 12 month working per year");
        }
    }
    
    private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
        int actualChildCount = Math.min(numberOfChildren, MAX_CHILDREN_FOR_TAX_DEDUCTION);
        int spouseDeduction = isMarried ? SPOUSE_NON_TAXABLE_INCOME : 0;
        int childrenDeduction = actualChildCount * CHILD_NON_TAXABLE_INCOME;
        
        return BASE_NON_TAXABLE_INCOME + spouseDeduction + childrenDeduction;
    }
    
    private static int calculateYearlyIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking) {
        return (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
    }
}