package example.bitznomad.SpringBatchWithBitznomad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa "generator" thành "strategy"
    private Long id;

    @Column(name = "full_name", nullable = false, updatable=false) // Thêm annotation để định nghĩa rõ ràng
    private String fullName;

    @Column(name = "fist_name", nullable = false) // Thêm annotation để định nghĩa rõ ràng
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;


}
