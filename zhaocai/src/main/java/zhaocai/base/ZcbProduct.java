package zhaocai.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ZcbProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long		id;
	
	@Column(nullable = false)
	public float yields;
	
	@Column(nullable = false)
	public int period;
	
	@Column(nullable = false)
	public boolean cashable;
	
	@Column(nullable = false)
	public String debtType;
	
	@Column(nullable = false)
	public int minNumber;
	
	@Column(nullable = false)
	public int  dealedNum;
	
	@Column(nullable = false)
	public int index;
	
	@Column(nullable = false)
	public long time;
}
