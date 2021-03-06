package io.anuke.novix.ui;

import io.anuke.ucore.core.Core;
import io.anuke.ucore.scene.ui.ImageButton;

public class FlipButton extends ImageButton{
	private boolean flipped;
	
	public FlipButton(boolean flipped){
		super(flipped ? "icon-up" : "icon-down");
		this.flipped = flipped;
		resizeImage(48);
		clicked(()->{
			flip();
		});
	}
	
	public boolean flipped(){
		return flipped;
	}
	
	public void flip(){
		flipped = !flipped;
		getStyle().imageUp = Core.skin.getDrawable(flipped ? "icon-up" : "icon-down");
	}
}
