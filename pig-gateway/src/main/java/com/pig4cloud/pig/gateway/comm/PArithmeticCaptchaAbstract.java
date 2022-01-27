package com.pig4cloud.pig.gateway.comm;

import com.googlecode.aviator.AviatorEvaluator;
import com.pig4cloud.captcha.ArithmeticCaptcha;
import com.pig4cloud.captcha.engine.Symbol;

public class PArithmeticCaptchaAbstract extends ArithmeticCaptcha {
	public PArithmeticCaptchaAbstract(){
		super();
	}
	public PArithmeticCaptchaAbstract(int width, int height) {
		this();
		this.setWidth(width);
		this.setHeight(height);
	}

	@Override
	protected char[] alphas() {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < this.len; ++i) {
			sb.append(num(difficulty));
			if (i < this.len - 1) {
				int type = num(1, algorithmSign);
				if (type == 1) {
					sb.append(Symbol.ADD.getValue());
				} else if (type == 2) {
					sb.append(Symbol.ADD.getValue());
				} else if (type == 3) {
					sb.append(Symbol.MUL.getValue());
				}
			}
		}

		this.chars = String.valueOf(AviatorEvaluator.execute(sb.toString().replace("x", "*")));
		sb.append("=?");
		setArithmeticString(sb.toString());
		return this.chars.toCharArray();
	}
}
