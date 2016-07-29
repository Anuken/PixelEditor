package net.pixelstatic.pixeleditor.ui;

import static net.pixelstatic.pixeleditor.modules.Main.s;
import net.pixelstatic.gdxutils.graphics.Hue;
import net.pixelstatic.gdxutils.graphics.Textures;
import net.pixelstatic.pixeleditor.graphics.Project;
import net.pixelstatic.pixeleditor.modules.Main;
import net.pixelstatic.pixeleditor.scene2D.BorderImage;
import net.pixelstatic.pixeleditor.scene2D.StaticPreviewImage;
import net.pixelstatic.utils.MiscUtils;
import net.pixelstatic.utils.scene2D.AnimatedImage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.*;

public class ProjectMenu extends VisDialog{
	private Main main;
	private VisScrollPane pane;
	
	public ProjectMenu(Main mainref){
		super("Projects");
		this.main = mainref;
		
		setFillParent(true);
		getTitleLabel().setColor(Color.CORAL);
		getTitleTable().row();
		getTitleTable().add(new Separator()).expandX().fillX().padTop(3 * s);
		
		VisTable scrolltable = new VisTable();

		pane = new VisScrollPane(scrolltable){
			public float getPrefHeight(){
				return Gdx.graphics.getHeight();
			}
		};
		pane.setFadeScrollBars(false);
		pane.setOverscroll(false, false);
		
		VisTable newtable = new VisTable();
		
		VisTextButton newbutton = new VisTextButton("New Project");
		newbutton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				main.projectmanager.newProject();
			}
		});

		MiscUtils.addIconToButton(newbutton, new Image(Textures.get("icon-plus")), 40 * s);
		
		VisTextButton settingsbutton = new VisTextButton("Settings");
		settingsbutton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				main.openSettingsMenu();
			}
		});
		

		newtable.left().add(newbutton).padBottom(6 * s).size(190 * s, 60 * s);
		newtable.left().add().grow();
		newtable.left().add(settingsbutton).padBottom(6 * s).size(120 * s, 60 * s).align(Align.topRight);

		getContentTable().add(newtable).grow().row();

		getContentTable().top().left().add(pane).align(Align.topLeft).grow();

		VisTextButton projectback = new VisTextButton("Back");
		projectback.add(new Image(Textures.getDrawable("icon-arrow-left"))).size(40 * s).center();

		projectback.getCells().reverse();
		projectback.getLabelCell().padRight(40f * s);

		getButtonsTable().add(projectback).width(Gdx.graphics.getWidth()).height(60 * s);
		setObject(projectback, false);
	}
	
	public ProjectTable update(boolean loaded){
		VisTable scrolltable = ((VisTable)((VisScrollPane)getContentTable().getCells().get(1).getActor()).getChildren().first());

		scrolltable.clearChildren();

		ProjectTable current = null;

		for(Project project : main.projectmanager.getProjects()){
			ProjectTable table = new ProjectTable(project, project == main.getCurrentProject() ? loaded : true);
			scrolltable.top().left().add(table).padTop(8).growX().padRight(10 * s).row();
			if(project == main.getCurrentProject()) current = table;
		}

		return current;
	}
	
	public VisDialog show(Stage stage){
		super.show(stage);
		stage.setScrollFocus(pane);
		return this;
	}
	
	public class ProjectTable extends VisTable{
		public final Project project;
		public boolean loaded;
		private boolean created;
		private Label sizelabel;
		private Cell<?> imagecell;

		public ProjectTable(final Project project, boolean startloaded){
			this.project = project;
			this.loaded = startloaded;

			VisLabel namelabel = new VisLabel(project.name);

			sizelabel = new VisLabel("Loading...");
			sizelabel.setColor(Color.GRAY);

			int imagesize = 40;

			VisImageButton openbutton = new VisImageButton(Textures.getDrawable(project == main.getCurrentProject() ? "icon-open-gray" : "icon-open"));
			VisImageButton copybutton = new VisImageButton(Textures.getDrawable("icon-copy"));
			VisImageButton renamebutton = new VisImageButton(Textures.getDrawable("icon-rename"));
			VisImageButton deletebutton = new VisImageButton(Textures.getDrawable("icon-trash"));

			if(project == main.getCurrentProject()){
				openbutton.setDisabled(true);
				openbutton.setColor(Hue.lightness(0.94f));
			}

			openbutton.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y){
					if(project != main.getCurrentProject()) main.projectmanager.openProject(project);
				}
			});

			copybutton.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y){
					main.projectmanager.copyProject(project);
				}
			});

			renamebutton.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y){
					main.projectmanager.renameProject(project);
				}
			});

			deletebutton.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y){
					main.projectmanager.deleteProject(project);
				}
			});

			openbutton.getImageCell().size(imagesize);
			copybutton.getImageCell().size(imagesize);
			renamebutton.getImageCell().size(imagesize);
			deletebutton.getImageCell().size(imagesize);

			VisTable texttable = new VisTable();
			VisTable buttontable = new VisTable();

			float bheight = 50, space = 4;

			buttontable.bottom().left().add(openbutton).align(Align.bottomLeft).height(bheight).growX().space(space);
			buttontable.add(copybutton).height(bheight).growX().space(space);
			buttontable.add(deletebutton).height(bheight).growX().space(space);
			buttontable.add(renamebutton).height(bheight).growX().space(space);

			top().left();

			background("button");
			setColor(Hue.lightness(0.87f));

			imagecell = stack(new AnimatedImage(Textures.getDrawable("icon-load-1"), Textures.getDrawable("icon-load-2"), Textures.getDrawable("icon-load-3")), new BorderImage());
			imagecell.padTop(imagecell.getPadTop() + 4).padBottom(imagecell.getPadBottom() + 4);

			MiscUtils.fitCell(imagecell, 128 * s, 1);

			add(texttable).grow();
			texttable.top().left().add(namelabel).padLeft(8).align(Align.topLeft);
			texttable.row();
			texttable.add(sizelabel).padLeft(8).padTop(10 * s).align(Align.topLeft);
			texttable.row();
			texttable.add(buttontable).grow().padLeft(8);

			addAction(new Action(){

				public boolean act(float delta){
					if(created) return true;
					if( !loaded) return false;

					if(project == main.getCurrentProject()) project.reloadTexture();

					Texture texture = project.cachedTexture;

					sizelabel.setText("Size: " + texture.getWidth() + "x" + texture.getHeight());

					StaticPreviewImage image = new StaticPreviewImage(texture);
					imagecell.setActor(image);

					MiscUtils.fitCell(imagecell, 128 * s, (float)texture.getWidth() / texture.getHeight());

					imagecell.padTop(imagecell.getPadTop() + 4).padBottom(imagecell.getPadBottom() + 4);

					pack();

					created = true;
					return true;
				}
			});

		}

	}
}