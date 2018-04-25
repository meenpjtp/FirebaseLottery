package project.senior.com.firebaselottery.Models;

public class ImageModel {
    private String image_url;

    public ImageModel() {
    }

    public ImageModel(String imageUrl) {
        image_url = imageUrl;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
