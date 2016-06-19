package net.pixelstatic.pixeleditor.scene2D;

import net.pixelstatic.pixeleditor.modules.GUI;
import net.pixelstatic.utils.scene2D.TextFieldDialogListener;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.*;

public class DialogClasses{
	static float s = GUI.s;
	
	public static class SizeDialog extends MenuDialog{
		VisTextField widthfield, heightfield;
		
		public SizeDialog(String title){
			super(title);
			
			widthfield = new VisTextField();
			heightfield = new VisTextField();
			widthfield.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());
			heightfield.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());

			widthfield.setText(GUI.gui.drawgrid.canvas.width() + "");
			heightfield.setText(GUI.gui.drawgrid.canvas.height() + "");

			TextFieldDialogListener.add(widthfield, true, 3);
			TextFieldDialogListener.add(heightfield, true, 3);
			
			getContentTable().add(new VisLabel("Width: ")).padLeft(50 * s).padTop(40 * s);
			getContentTable().add(widthfield).size(140, 40).padRight(50 * s).padTop(40 * s);

			getContentTable().row();

			getContentTable().add(new VisLabel("Height: ")).padLeft(50 * s).padTop(40 * s).padBottom(40f * s);
			getContentTable().add(heightfield).size(140, 40).padRight(50 * s).padTop(40 * s).padBottom(40f * s);

			getContentTable().row();
			
			addButtons();
		}
		
		protected void result(Object object){
			if((Boolean)object != true) return;

			try{
				int width = Integer.parseInt(widthfield.getText());
				int height = Integer.parseInt(heightfield.getText());

				result(width, height);
			}catch(Exception e){
				e.printStackTrace();
				Dialogs.showDetailsDialog(getStage(), "An exception has occured.", "Error", e.getClass().getSimpleName() + ": " + (e.getMessage() == null ? "" : e.getMessage()));
			}
		}
		
		public void result(int width, int height){
			
		}
		
	}
	
	public static class FlipDialog extends MenuDialog{
		public FlipDialog(){
			super("Flip Image");
			
			final VisCheckBox hbox = new VisCheckBox("Flip Horizontal");
			final VisCheckBox vbox = new VisCheckBox("Flip Vertical");
			
			Table table = getContentTable();
			
			table.add(hbox).row();
			table.add(vbox);
			
			addButtons();
			
		}
	}
	
	
	public static abstract class MenuDialog extends VisDialog{
		
		public MenuDialog(String title){
			super(title, "dialog");
		}
		
		void addButtons(){
			Button cancel = new VisTextButton("Cancel");
			Button ok = new VisTextButton("OK");

			setObject(ok, true);
			setObject(cancel, false);

			getButtonsTable().add(cancel).size(130 * s, 60 * s);
			getButtonsTable().add(ok).size(130 * s, 60 * s);
			addCloseButton();
		}
		
		protected void result(Object object){
			if((Boolean)object != true) return;

			try{
				
			}catch(Exception e){
				e.printStackTrace();
				Dialogs.showDetailsDialog(getStage(), "An exception has occured.", "Error", e.getClass().getSimpleName() + ": " + (e.getMessage() == null ? "" : e.getMessage()));
			}
		}
	}
}
