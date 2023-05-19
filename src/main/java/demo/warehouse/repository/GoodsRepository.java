package demo.warehouse.repository;

import demo.warehouse.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Goods findByName(String name);
}
