package com.pig4cloud.pig.casee.feign.fallback;

import com.pig4cloud.pig.casee.feign.RemoteAssetsRePaifuService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteAssetsRePaifuServiceFallbackImpl implements RemoteAssetsRePaifuService {

	@Setter
	private Throwable cause;

}
