package zhaocai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import zhaocai.entity.ZcbDeal;

public interface ZcbDealDao extends JpaRepository<ZcbDeal, Long>, JpaSpecificationExecutor<ZcbDeal>
{

}
