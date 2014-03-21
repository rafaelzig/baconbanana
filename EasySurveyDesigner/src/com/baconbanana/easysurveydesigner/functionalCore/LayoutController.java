package com.baconbanana.easysurveydesigner.functionalCore;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LayoutController {
	
	private static GridBagConstraints bagCon;
	/**
	 * 
	 * 
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @param weightx
	 * @param weighty
	 * @param anchor
	 * @param fill
	 * @param insets
	 * @param ipadx
	 * @param ipady
	 * @return
	 */
	public static GridBagConstraints summonCon(int gridx, int gridy, int gridwidth,  int gridheight, double weightx, double weighty, int anchor, int fill, Insets insets, int ipadx, int ipady){
		bagCon = new GridBagConstraints(gridx, gridy, gridwidth,  gridheight, weightx, weighty, anchor, fill, insets, ipadx,  ipady);
		return bagCon;
	}
	/**
	 * 
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @param weightx
	 * @param weighty
	 * @param anchor
	 * @param fill
	 * @return
	 */
	public static GridBagConstraints summonCon(int gridx, int gridy, int gridwidth,  int gridheight, double weightx, double weighty, int anchor, int fill){
		bagCon = new GridBagConstraints(gridx, gridy, gridwidth,  gridheight, weightx, weighty, anchor, fill, bagCon.insets, bagCon.ipadx,  bagCon.ipady);
		return bagCon;
	}
	/**
	 * 
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @param weightx
	 * @param weighty
	 * @return
	 */
	public static GridBagConstraints summonCon(int gridx, int gridy, int gridwidth,  int gridheight, double weightx, double weighty){
		bagCon = new GridBagConstraints(gridx, gridy, gridwidth,  gridheight, weightx, weighty, bagCon.anchor, bagCon.fill, bagCon.insets, bagCon.ipadx,  bagCon.ipady);
		return bagCon;
	}
	/**
	 * 
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @return
	 */
	public static GridBagConstraints summonCon(int gridx, int gridy, int gridwidth,  int gridheight){
		bagCon = new GridBagConstraints(gridx, gridy, gridwidth,  gridheight, bagCon.weightx, bagCon.weighty, bagCon.anchor, bagCon.fill, bagCon.insets, bagCon.ipadx,  bagCon.ipady);
		return bagCon;
	}
	/**
	 * 
	 * @param gridx
	 * @param gridy
	 * @return
	 */
	public static GridBagConstraints summonCon(int gridx, int gridy){
		bagCon = new GridBagConstraints(gridx, gridy, bagCon.gridwidth,  bagCon.gridheight, bagCon.weightx, bagCon.weighty, bagCon.anchor, bagCon.fill, bagCon.insets, bagCon.ipadx,  bagCon.ipady);
		return bagCon;
	}
	/**
	 * 
	 * @param gridx
	 * @return
	 */
	public static GridBagConstraints summonConX(int gridx){
		bagCon = new GridBagConstraints(gridx, bagCon.gridy, bagCon.gridwidth, bagCon.gridheight, bagCon.weightx, bagCon.weighty, bagCon.anchor, bagCon.fill, bagCon.insets, bagCon.ipadx,  bagCon.ipady);
		return bagCon;
	}
	/**
	 * 
	 * @param gridy
	 * @return
	 */
	public static GridBagConstraints summonConY(int gridy){
		bagCon = new GridBagConstraints(bagCon.gridx, gridy, bagCon.gridwidth,  bagCon.gridheight, bagCon.weightx, bagCon.weighty, bagCon.anchor, bagCon.fill, bagCon.insets, bagCon.ipadx,  bagCon.ipady);
		return bagCon;
	}
	
}
