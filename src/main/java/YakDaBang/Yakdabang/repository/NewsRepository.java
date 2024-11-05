package YakDaBang.Yakdabang.repository;

import YakDaBang.Yakdabang.domain.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

}
