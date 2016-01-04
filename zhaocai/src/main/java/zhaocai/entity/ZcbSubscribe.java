package zhaocai.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zcb_subscribe")
public class ZcbSubscribe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long		id;
	
	@Column(nullable = false)
	public float yields;
	
	@Column(nullable = false)
	public int period;
	
	@Column(nullable = false)
	public float totalDeal;
}
