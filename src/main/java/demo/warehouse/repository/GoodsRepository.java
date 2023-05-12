package demo.warehouse.repository;

import demo.warehouse.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Goods findByid(Long id);

    Goods findByName(String name);
}
