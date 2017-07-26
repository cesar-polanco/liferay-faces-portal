/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.demos.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.FacesContextHelperFactory;

import com.liferay.portal.kernel.captcha.CaptchaUtil;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class CaptchaBacking {

	@ManagedProperty(value = "#{showcaseModelBean.selectedComponent.required")
	private boolean requiredChecked;

	private String captchaText;

	public String getCaptchaImpl() {
		return CaptchaUtil.getCaptcha().getClass().getName();
	}

	public String getCaptchaText() {
		return captchaText;
	}

	public boolean isRequiredChecked() {
		return requiredChecked;
	}

	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}

	public void setRequiredChecked(boolean requiredChecked) {

		// Injected via @ManagedProperty
		this.requiredChecked = requiredChecked;
	}

	public void submit() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				externalContext);

		// If the user entered non-blank value for the captcha then validation was successful in the
		// PROCESS_VALIDATIONS phase of the JSF lifecycle.
		if ((captchaText != null) && (captchaText.trim().length() > 0)) {

			facesContextHelper.addGlobalInfoMessage(facesContext, "you-entered-the-correct-text-verification-code");
		}

		// Otherwise, the user entered a blank value for the captcha.
		else {

			if (!requiredChecked) {

				facesContextHelper.addGlobalInfoMessage(facesContext,
					"no-value-was-entered-for-the-non-required-captcha");
			}
		}
	}
}
