package demo.warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private String operatorName;

    @Column(nullable = false)
    private String goods;

    @Column(nullable = false)
    private Integer size;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "`listed_at`")
    @CreationTimestamp
    private Date listedAt;
}
