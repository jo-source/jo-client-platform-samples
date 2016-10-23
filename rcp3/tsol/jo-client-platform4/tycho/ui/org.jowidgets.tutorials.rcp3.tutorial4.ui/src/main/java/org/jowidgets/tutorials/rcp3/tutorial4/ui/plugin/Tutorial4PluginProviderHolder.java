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

package org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin;

import org.jowidgets.cap.ui.api.plugin.IBeanFormPlugin;
import org.jowidgets.cap.ui.api.plugin.IBeanProxyLabelRendererPlugin;
import org.jowidgets.cap.ui.api.plugin.IBeanRelationTreeModelPlugin;
import org.jowidgets.cap.ui.api.plugin.IBeanTableMenuContributionPlugin;
import org.jowidgets.cap.ui.api.plugin.IBeanTableMenuInterceptorPlugin;
import org.jowidgets.plugin.tools.PluginProviderBuilder;
import org.jowidgets.plugin.tools.PluginProviderHolder;
import org.jowidgets.tutorials.rcp3.tutorial4.common.bean.IAuthorization;
import org.jowidgets.tutorials.rcp3.tutorial4.common.bean.IPerson;
import org.jowidgets.tutorials.rcp3.tutorial4.common.bean.IRole;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.bean.AuthorizationRendererPlugin;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.bean.PersonRendererPlugin;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.bean.RoleRendererPlugin;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.form.RoleFormPlugin;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.table.AuthorizationTableMenuInterceptorPlugin;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.table.PersonTableMenuContributionPlugin;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.table.PersonTableMenuInterceptorPlugin;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.table.RoleTableMenuInterceptorPlugin;
import org.jowidgets.tutorials.rcp3.tutorial4.ui.plugin.tree.RelationTreeModelPlugin;

public final class Tutorial4PluginProviderHolder extends PluginProviderHolder {

	public Tutorial4PluginProviderHolder() {
		super(new Tutorial4PluginProviderBuilder(), 2);
	}

	private static final class Tutorial4PluginProviderBuilder extends PluginProviderBuilder {

		Tutorial4PluginProviderBuilder() {
			addPlugin(IBeanRelationTreeModelPlugin.ID, new RelationTreeModelPlugin());

			addMenuInterceptorPlugin(new PersonTableMenuInterceptorPlugin(), IPerson.class);
			addMenuInterceptorPlugin(new RoleTableMenuInterceptorPlugin(), IRole.class);
			addMenuInterceptorPlugin(new AuthorizationTableMenuInterceptorPlugin(), IAuthorization.class);

			addMenuContributionPlugin(new PersonTableMenuContributionPlugin(), IPerson.class);

			addBeanRenderPlugin(new PersonRendererPlugin(), IPerson.class);
			addBeanRenderPlugin(new RoleRendererPlugin(), IRole.class);
			addBeanRenderPlugin(new AuthorizationRendererPlugin(), IAuthorization.class);

			addBeanFormPlugin(new RoleFormPlugin(), IRole.class);
		}

		private void addMenuInterceptorPlugin(final IBeanTableMenuInterceptorPlugin<?> plugin, final Class<?>... beanTypes) {
			addPlugin(
					IBeanTableMenuInterceptorPlugin.ID,
					plugin,
					IBeanTableMenuInterceptorPlugin.BEAN_TYPE_PROPERTY_KEY,
					beanTypes);
		}

		private void addMenuContributionPlugin(final IBeanTableMenuContributionPlugin<?> plugin, final Class<?>... beanTypes) {
			addPlugin(
					IBeanTableMenuContributionPlugin.ID,
					plugin,
					IBeanTableMenuContributionPlugin.BEAN_TYPE_PROPERTY_KEY,
					beanTypes);
		}

		private void addBeanRenderPlugin(final IBeanProxyLabelRendererPlugin<?> plugin, final Class<?>... beanTypes) {
			addPlugin(IBeanProxyLabelRendererPlugin.ID, plugin, IBeanProxyLabelRendererPlugin.BEAN_TYPE_PROPERTY_KEY, beanTypes);
		}

		private void addBeanFormPlugin(final IBeanFormPlugin plugin, final Class<?>... beanTypes) {
			addPlugin(IBeanFormPlugin.ID, plugin, IBeanFormPlugin.BEAN_TYPE_PROPERTY_KEY, beanTypes);
		}

	}

}
