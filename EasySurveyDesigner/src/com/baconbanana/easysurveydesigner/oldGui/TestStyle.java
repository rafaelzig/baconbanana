package com.baconbanana.easysurveydesigner.oldGui;


import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;


/**
 * This class describes a theme using "primary" colors.
 * You can change the colors to anything else you want.
 *
 * 1.9 07/26/04
 */
public class TestStyle extends DefaultMetalTheme {

    public String getName() { return "Toms"; }

    private final ColorUIResource primary1 = new ColorUIResource(51, 204, 255);
    private final ColorUIResource primary2 = new ColorUIResource(179, 224, 255);
    private final ColorUIResource primary3 = new ColorUIResource(0, 172, 230);

    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }

}