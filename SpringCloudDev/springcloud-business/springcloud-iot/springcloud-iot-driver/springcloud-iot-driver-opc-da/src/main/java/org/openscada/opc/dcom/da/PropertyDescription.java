/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * OpenSCADA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * only, as published by the Free Software Foundation.
 *
 * OpenSCADA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License version 3 for more details
 * (a copy is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with OpenSCADA. If not, see
 * <http://opensource.org/licenses/lgpl-3.0.html> for a copy of the LGPLv3 License.
 */package org.openscada.opc.dcom.da;

public class PropertyDescription {
    private int _id = -1;

    private String _description = "";

    private short _varType = 0;

    public String getDescription() {
        return this._description;
    }

    public void setDescription(final String description) {
        this._description = description;
    }

    public int getId() {
        return this._id;
    }

    public void setId(final int id) {
        this._id = id;
    }

    public short getVarType() {
        return this._varType;
    }

    public void setVarType(final short varType) {
        this._varType = varType;
    }
}
