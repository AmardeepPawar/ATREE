package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageAndImageViews {
	ImageView modNotFndImgView;
	Image modNtImg;
	static ImageAndImageViews clsInst = new ImageAndImageViews();

	private ImageAndImageViews() {
		modNtImg = new Image("file:" + AtreeFolder.getInstance().getATreeFolder() + "//icons//error.jpg");
	}
	
	public static ImageAndImageViews getClassInstance()
	{
		if(clsInst == null)
		{
			clsInst = new ImageAndImageViews();
		}
		return clsInst;
	}

	public ImageView getModNotFndImgView() {
		modNotFndImgView = new ImageView(modNtImg);
		return modNotFndImgView;
	}

	public void setModNotFndImgView(ImageView modNotFndImgView) {

		this.modNotFndImgView = modNotFndImgView;
	}

	public Image getModNtImg() {
		return modNtImg;
	}

	public void setModNtImg(Image modNtImg) {
		this.modNtImg = modNtImg;
	}
}
