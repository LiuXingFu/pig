/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.vo.paifu;

import com.pig4cloud.pig.casee.entity.Auction;
import lombok.Data;
import java.util.List;

/**
 * 拍卖表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@Data
public class AuctionDetailVO extends Auction {

    List<AssetsRePaifuDetailVO> assetsReList;

    private Integer assetsReId;


}
