package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 *	实体执行财产程序
 */
@Data
public class EntityZX extends CommonalityData implements Serializable {

	/**
	 * 实体执行财产
	 */
	EntityZX_STZX entityZX_STZX = new EntityZX_STZX();

}
