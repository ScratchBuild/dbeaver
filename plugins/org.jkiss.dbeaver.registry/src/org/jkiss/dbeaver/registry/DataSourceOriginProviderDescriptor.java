/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2020 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jkiss.dbeaver.registry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.model.DBPDataSourceOriginProvider;
import org.jkiss.dbeaver.model.impl.AbstractDescriptor;

/**
 * DataSourceOriginProviderDescriptor
 */
public class DataSourceOriginProviderDescriptor extends AbstractDescriptor
{
    private static final Log log = Log.getLog(DataSourceOriginProviderDescriptor.class);

    private final String id;
    private final String label;
    private final ObjectType implType;
    private DBPDataSourceOriginProvider provider;

    public DataSourceOriginProviderDescriptor(IConfigurationElement config)
    {
        super(config);

        this.id = config.getAttribute(RegistryConstants.ATTR_ID);
        this.label = config.getAttribute(RegistryConstants.ATTR_LABEL);
        this.implType = new ObjectType(config);
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public DBPDataSourceOriginProvider getProvider() {
        if (provider == null) {
            try {
                provider = implType.getObjectClass(DBPDataSourceOriginProvider.class).getConstructor().newInstance();
            } catch (Exception e) {
                throw new IllegalStateException("Error instantiating datasource origin provider " + id, e);
            }
        }
        return provider;
    }
}
