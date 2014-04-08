/*
 * Copyright (c) 2013, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.tutorials.rcp3.tutorial1.service.security;

import java.util.HashSet;
import java.util.Set;

import org.jowidgets.security.api.IAuthorizationService;
import org.jowidgets.security.api.IPrincipal;
import org.jowidgets.security.tools.DefaultPrincipal;

public final class Tutorial1AuthorizationService implements IAuthorizationService<IPrincipal<String>> {

	private static final String READ_ALL = "READ_ALL";
	private static final String WRITE_ALL = "WRITE_ALL";

	private static final Set<String> ADMIN_AUTHORITIES = createAdminAuthorities();
	private static final Set<String> GUEST_AUTHORITIES = createGuestAuthorities();

	private static Set<String> createAdminAuthorities() {
		final Set<String> result = new HashSet<String>();
		result.add(READ_ALL);
		result.add(WRITE_ALL);
		return result;
	}

	private static Set<String> createGuestAuthorities() {
		final Set<String> result = new HashSet<String>();
		result.add(READ_ALL);
		return result;
	}

	@Override
	public IPrincipal<String> authorize(final IPrincipal<String> principal) {
		if ("admin".equals(principal.getUsername())) {
			return new DefaultPrincipal(principal.getUsername(), ADMIN_AUTHORITIES);
		}
		else {
			return new DefaultPrincipal(principal.getUsername(), GUEST_AUTHORITIES);
		}
	}
}
