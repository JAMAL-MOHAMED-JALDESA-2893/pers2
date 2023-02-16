package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class paid_allowances {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long allowance_custom_fk;
    private Long emp_id;
    private Long payroll_id;
    private String description;
    private Double amount;
}
