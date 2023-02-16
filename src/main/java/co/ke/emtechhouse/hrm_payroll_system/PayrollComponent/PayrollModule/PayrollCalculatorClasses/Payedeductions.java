package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations.Kra;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations.KraRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Payedeductions {
    private final KraRepo kraRepo;

    @Autowired
    private DcrsCalculator dcrsCalculator;
    @Autowired
    private SalaryCalculator salaryCalculator;

    public Payedeductions(KraRepo kraRepo) {
        this.kraRepo = kraRepo;
    }
    //    TODO: 1.Get Personal relief
    public Double getPersonalRelief(){
        Double personalRelief=0.0;
        try {
            Kra kra_one_query = kraRepo.findKra();
            personalRelief = kra_one_query.getPersonal_relief_monthly();
            log.info("personal relief is"+personalRelief.toString());
        }catch(Exception e) {
            log.info("PAYE Deductions Error {} "+e);
        }
        return personalRelief;
    }

    //    TODO: 1.Get PAYE
    public Double getPayededuction(/**Double salary, Double total_non_cash_benefit,Double dcrs_actual_e2, Double owner_occupied_interests, Double value_of_quarters,**/ Double insuranceRelief, /**Double taxable_allowances, Double nssfDeductions**/
                                   Double basic_salary, Double gross_salary,Double mortgageInterest){
        Double paye = 0.00;
        try {
            double taxRemainder = 0.00;
            double firstTax = 0.00;
            double secondTax = 0.00;
            double thirdTax = 0.00;
            double  taxDeductions = 0.00;
            double tax_charged = 0.00;
//            double taxable_pay  = salary + total_non_cash_benefit + value_of_quarters + taxable_allowances - nssfDeductions;
//            Double dcrs_e1 = (0.3 * taxable_pay);
//            Double dcrs_fixed_e3 = 20000.00;
//            Double smallest_dcrs = Math.min(dcrs_e1, Math.min(dcrs_actual_e2, dcrs_fixed_e3));
////            Calculate the retirement & owner occupied interest
//            Double retirement_and_owner_occupied_interests = smallest_dcrs + owner_occupied_interests;
//            Double chargeable_pay = taxable_pay - retirement_and_owner_occupied_interests;
//            //            get personal relief
//            Kra kra_one_query = kraRepo.findKra();
//            Double personalRelief = kra_one_query.getPersonal_relief_monthly();
//            Double total_relief = personalRelief + insuranceRelief;


            //dcrsCalculator
//            Double basic_salary=0.0;
//            Double gross_salary=0.0;
//            Double mortgageInterest=0.0;
            Double dcrs_e1=dcrsCalculator.calculateDcrsE1(basic_salary);
            Double dcrs_actual_e2 = dcrsCalculator.calculateDcrsE2(basic_salary);
            Double dcrs_fixed_e3= dcrsCalculator.calculateDcrsE3();
            Double owner_occupied_interests=dcrsCalculator.calculateOwnerOccupiedInterest();
            Double retirement_and_owner_occupied_interests = dcrsCalculator.calcRitirementContrAndOwnerOccupiedInterest(dcrs_e1,dcrs_actual_e2,dcrs_fixed_e3,owner_occupied_interests);
            //getting taxable income
            Double chargeable_pay= salaryCalculator.calculateTaxableSalary(gross_salary,mortgageInterest,retirement_and_owner_occupied_interests);
            log.info("taxable amount is"+chargeable_pay.toString());
            Double personalRelief=getPersonalRelief();
            Double total_relief = personalRelief + insuranceRelief;

            //           Algorithim for PAYE
//            *******************************************************************
//            double personalRelief = 2400.00;

//            get Tax Band 1 24000 = 10%
            Kra tax_band_1 = kraRepo.findTaxBand1Kra();
            Double monthlyAmount1 = tax_band_1.getAmount_monthly();
            Double rate_tax_band_1 = tax_band_1.getRate()/100;
//            get Tax Band 2  8,333.00 = 25%
            Kra tax_band_2 = kraRepo.findTaxBand2Kra();
            Double monthlyAmount2 = tax_band_2.getAmount_monthly();
            Double rate_tax_band_2 = tax_band_2.getRate()/100;
//            get Tax Band 3 32,333.00 = 30%
            Kra tax_band_3 = kraRepo.findTaxBand3Kra();
            Double monthlyAmount3 = tax_band_3.getAmount_monthly();
            Double rate_tax_band_3 = tax_band_3.getRate()/100;
            if (chargeable_pay>monthlyAmount1){
//                firstTax
                firstTax = monthlyAmount1 * rate_tax_band_1;
                double firstRemainder = chargeable_pay - monthlyAmount1;
//                        remdainder < 8333
                if (firstRemainder < monthlyAmount2 ){
                    secondTax = firstRemainder * rate_tax_band_2;

                }else {
//                            greather than 8333
                    secondTax = monthlyAmount2 * rate_tax_band_2;

                    double thirdRemainder = chargeable_pay - monthlyAmount1 - monthlyAmount2;
                    thirdTax = thirdRemainder * rate_tax_band_3;
                }

                tax_charged = (firstTax + secondTax + thirdTax);
                paye = tax_charged - total_relief;
            }else {
                paye = 0.00;
            }
        }catch (Exception e) {
            log.info("PAYE Deductions Error {} "+e);
        }
        return  paye;
    }
}
