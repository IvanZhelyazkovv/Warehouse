package demo.warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listing")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firmName;

    @Column(nullable = false)
    private String goods;

    @Column(nullable = false)
    private Integer size;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "`listed_at`")
    @CreationTimestamp
    private Date listedAt;

    @Column(name = "accepted_at")
    @UpdateTimestamp
    private Date acceptedAt;

    @Column(name = "worked_by")
    private String workedBy;

    @Column(name = "status")
    private String status = "pending";
}
