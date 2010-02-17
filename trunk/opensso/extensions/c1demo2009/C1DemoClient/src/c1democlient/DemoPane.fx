/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: DemoPane.fx,v 1.2 2009/06/11 05:29:43 superpat7 Exp $
 */

package c1democlient;

import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextOrigin;
import c1democlient.Constants;

public class DemoPane extends CustomNode {
    public var currentPane: Node;
    public override function create():Node {
        Group {
            content: [
                VBox {
                    translateX: 15
                    translateY: 20
                    spacing: 10
                    content: bind [
                        Text {
                            font: Constants.arialBoldItalic18
                            fill: Constants.sunBlue
                            content: currentPane.id;
                            textOrigin: TextOrigin.TOP
                        },
                        currentPane
                    ]
                }
            ]
        };
    }
}
