/**
 * 
 */
package zhaocai.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hzren
 *
 */
@Entity
@Table(name = "zcb_deal_data")
public class ZcbDeal
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long		id;

	@Column(nullable = false)
	public Date		fetchTime;

	@Column(nullable = false)
	public float	appt7dAmtSum;

	@Column(nullable = false)
	public float	allEverAmtSum;

	@Column(nullable = false)
	public String	reportDate;

	@Column(nullable = false)
	public int		appt7dRate;
}
