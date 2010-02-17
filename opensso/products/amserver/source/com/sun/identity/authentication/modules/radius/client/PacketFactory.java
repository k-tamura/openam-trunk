/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007 Sun Microsystems Inc. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://opensso.dev.java.net/public/CDDLv1.0.html or
 * opensso/legal/CDDLv1.0.txt
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at opensso/legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * $Id: PacketFactory.java,v 1.2 2008/06/25 05:42:02 qcheng Exp $
 *
 */



package com.sun.identity.authentication.modules.radius.client;

import java.util.*;
import java.math.*;
import java.security.*;
import java.net.*;
import java.io.*;

public class PacketFactory
{
	public static ServerPacket createServerPacket(byte data[])
		throws IOException
	{
		switch (data[0] & 0xFF)
		{
		case Packet.ACCESS_ACCEPT:
			return new AccessAccept(data);
		case Packet.ACCESS_REJECT:
			return new AccessReject(data);
		case Packet.ACCESS_CHALLENGE:
			return new AccessChallenge(data);
		default:
			throw new IOException("Unknown server packet " + (data[0] & 0xFF));
		}
	}
}
