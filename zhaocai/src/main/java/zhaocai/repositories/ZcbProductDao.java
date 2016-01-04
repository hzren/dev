package zhaocai.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import zhaocai.base.ZcbProduct;

@NoRepositoryBean
public interface ZcbProductDao<T extends ZcbProduct>  extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
	Page<T> findByIndex(int index, Pageable pageable);
}
