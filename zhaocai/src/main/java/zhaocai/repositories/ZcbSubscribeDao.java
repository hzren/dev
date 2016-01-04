package zhaocai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import zhaocai.entity.ZcbSubscribe;

public interface ZcbSubscribeDao  extends JpaRepository<ZcbSubscribe, Long>, JpaSpecificationExecutor<ZcbSubscribe> {

}
